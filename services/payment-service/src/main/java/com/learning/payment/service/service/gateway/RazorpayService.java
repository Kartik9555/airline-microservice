package com.learning.payment.service.service.gateway;

import com.learning.common.payload.dto.UserDTO;
import com.learning.common.payload.response.PaymentLinkResponse;
import com.learning.payment.service.model.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    @Value("${razorpay.api.key-id}")
    private String razorpayKey;
    @Value("${razorpay.api.key-secret}")
    private String razorpaySecret;
    @Value("${razorpay.callback.base-url}")
    private String callBackBaseUrl;

    public PaymentLinkResponse createPaymentLink(UserDTO user, Payment payment) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);
        final BigDecimal amount= BigDecimal.valueOf(payment.getAmount());
        final Long amountInPaisa = amount.multiply(BigDecimal.valueOf(100)).longValue();
        final JSONObject paymentLinkRequest = getJsonObject(user, payment, amountInPaisa);
        PaymentLink paymentLink = client.paymentLink.create(paymentLinkRequest);
        final String paymentUrl = paymentLink.get("short_url");
        final String paymentLinkId = paymentLink.get("id");
        return PaymentLinkResponse.builder()
                .paymentLinkUrl(paymentUrl)
                .paymentLinkId(paymentLinkId)
                .build();
    }

    public JSONObject fetchPaymentDetails(String paymentId) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);
        com.razorpay.Payment payment = client.payments.fetch(paymentId);
        return payment.toJson();
    }

    private @NonNull JSONObject getJsonObject(UserDTO user, Payment payment, Long amountInPaisa) {
        final JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amountInPaisa);
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("description", payment.getTransactionId());

        final JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());
        if(user.getPhone() != null) {
            customer.put("contact", user.getPhone());
        }
        paymentLinkRequest.put("customer", customer);

        final JSONObject notify = new JSONObject();
        notify.put("sms", user.getPhone() != null);
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);

        final String successUrl = callBackBaseUrl + "/booking-success/" + payment.getBookingId();
        paymentLinkRequest.put("callback_url", successUrl);
        paymentLinkRequest.put("callback_method", "get");

        final JSONObject notes = new JSONObject();
        notes.put("user_id", user.getId());
        notes.put("payment_id", payment.getId());
        notes.put("booking_id", payment.getBookingId());
        paymentLinkRequest.put("notes", notes);
        return paymentLinkRequest;
    }

}
