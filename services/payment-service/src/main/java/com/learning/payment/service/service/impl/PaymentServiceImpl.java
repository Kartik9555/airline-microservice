package com.learning.payment.service.service.impl;

import com.learning.common.enums.PaymentProvider;
import com.learning.common.enums.PaymentStatus;
import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.dto.UserDTO;
import com.learning.common.payload.request.PaymentInitiateRequest;
import com.learning.common.payload.request.PaymentVerifyRequest;
import com.learning.common.payload.response.PaymentInitiateResponse;
import com.learning.common.payload.response.PaymentLinkResponse;
import com.learning.payment.service.event.PaymentEventProducer;
import com.learning.payment.service.mapper.PaymentMapper;
import com.learning.payment.service.model.Payment;
import com.learning.payment.service.repository.PaymentRepository;
import com.learning.payment.service.service.PaymentService;
import com.learning.payment.service.service.gateway.RazorpayService;
import com.learning.payment.service.service.outbound.UserOutboundService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final UserOutboundService userService;
    private final PaymentEventProducer producer;

    @Override
    @Transactional
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException {
        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(payment -> {
                    if(payment.getStatus() == PaymentStatus.SUCCESS) {
                        throw new RuntimeException("Payment already completed for booking id: " + request.getBookingId());
                    }
                });
        final Payment payment = PaymentMapper.toPayment(request);
        payment.setTransactionId(generateTransactionId());
        final Payment saved = paymentRepository.save(payment);
        final PaymentInitiateResponse response = PaymentMapper.toPayment(saved);
        if (request.getProvider() == PaymentProvider.RAZORPAY) {
            final UserDTO user = userService.getUserById(request.getUserId());
            final PaymentLinkResponse paymentLinkResponse = razorpayService.createPaymentLink(user, saved);
            response.setCheckoutUrl(paymentLinkResponse.getPaymentLinkUrl());
            response.setProviderPaymentId(paymentLinkResponse.getPaymentLinkId());
        }
        return response;
    }

    @Override
    @Transactional
    public PaymentDTO verifyPayment(PaymentVerifyRequest request) throws Exception {
        final JSONObject paymentDetails = razorpayService.fetchPaymentDetails(request.getRazorpayPaymentId());
        final String status = paymentDetails.optString("status");
        final JSONObject notes = paymentDetails.optJSONObject("notes");
        final Long paymentId = Long.parseLong(notes.optString("payment_id"));
        final String orderId = paymentDetails.optString("order_id");
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new Exception("Payment not found for id: " + paymentId));

        boolean isValid = "captured".equalsIgnoreCase(status);
        if(isValid) {
            if(payment.getProvider() == PaymentProvider.RAZORPAY) {
                payment.setProviderPaymentId(request.getRazorpayPaymentId());
            }
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(Instant.now());
            payment.setOrderId(orderId);
            paymentRepository.save(payment);
            producer.sendPaymentCompleted(payment);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment Verification Failed");
            paymentRepository.save(payment);
            producer.sendPaymentFailed(payment);
        }
        return PaymentMapper.toPaymentDTO(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(PaymentMapper::toPaymentDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, PaymentDTO> getPaymentByBookingIds(List<Long> bookingIds) {
        return paymentRepository.findAllByBookingIdIn(bookingIds)
                .stream()
                .map(PaymentMapper::toPaymentDTO)
                .collect(Collectors.toMap(PaymentDTO::getId, Function.identity()));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long paymentId) throws Exception {
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new Exception("Payment not found with id: " + paymentId));
        return PaymentMapper.toPaymentDTO(payment);
    }

    private String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
