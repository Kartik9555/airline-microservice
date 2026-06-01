package com.learning.notification.service.service;

import com.learning.common.event.BookingConfirmedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${notification.from-email}")
    private String fromEmail;

    @Value("${notification.from-name}")
    private String fromName;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);

    public void sendBookingConfirmation(BookingConfirmedEvent event) throws MessagingException {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setFrom(fromName);
        helper.setTo(event.getContactEmail());
        helper.setSubject(buildSubject(event));
        helper.setText(buildHtmlBody(event), true);

    }

    private String buildHtmlBody(BookingConfirmedEvent event) {
        final Context context = new Context();
        context.setVariable("booking", event);
        context.setVariable("passengerCount", event.getPassengers() != null ? event.getPassengers().size() : 1);
        context.setVariable("depDate", event.getDepartureDateTime() != null ? DATE_FMT.format(event.getDepartureDateTime()) : "N/A");
        context.setVariable("depTime", event.getDepartureDateTime() != null ? TIME_FMT.format(event.getDepartureDateTime()) : "N/A");
        context.setVariable("arrDate", event.getArrivalDateTime() != null ? DATE_FMT.format(event.getArrivalDateTime()) : "N/A");
        context.setVariable("arrTime", event.getArrivalDateTime() != null ? TIME_FMT.format(event.getArrivalDateTime()) : "N/A");
        context.setVariable("paidAt", event.getPaidAt() != null ? DT_FMT.format(event.getPaidAt()) : "N/A");
        context.setVariable("bookingDate", event.getBookingDate() != null ? DT_FMT.format(event.getBookingDate()) : "N/A");

        double base = orZero(event.getBaseFare());
        double taxes = orZero(event.getTaxesAndFees());
        double seats = orZero(event.getSeatFees());
        double ancillary = orZero(event.getAncillaryFees());
        double meals = orZero(event.getMealFees());
        double total = orZero(event.getTotalAmount());

        context.setVariable("baseFareTotal", fmt(base));
        context.setVariable("taxes", fmt(taxes));
        context.setVariable("seatFees", fmt(seats));
        context.setVariable("ancillaryFees",fmt(ancillary));
        context.setVariable("mealFees", fmt(meals));
        context.setVariable("totalAmount", fmt(total));

        context.setVariable("hasBaggage", event.getCheckinBaggagePieces() != null || event.getCabinBaggagePieces() != null);
        context.setVariable("checkinBaggage", baggageLabel(event.getCheckinBaggagePieces(), event.getCheckinBaggageWeightPerPiece()));
        context.setVariable("cabinBaggage", baggageLabel(event.getCabinBaggagePieces(), event.getCabinBaggageWeightPerPiece()));

        context.setVariable("cabinClassDisplay", cabinDisplayName(event.getCabinClass()));
        return templateEngine.process("email/booking-confirmation", context);
    }

    private static String cabinDisplayName(String cabinClass) {
        if(cabinClass == null) return "Economy";
        return switch (cabinClass.toUpperCase()) {
            case "ECONOMY" -> "Economy";
            case "PREMIUM_ECONOMY" -> "Premium Economy";
            case "BUSINESS" -> "Business";
            case "FIRST" -> "First Class";
            default -> cabinClass;
        };
    }

    private String baggageLabel(Integer pieces, Double weightPerPiece) {
        if(pieces == null && weightPerPiece == null) return "Not included";
        if(pieces != null && weightPerPiece != null) return pieces + " \u00d7 " + weightPerPiece.intValue() + " kg";
        if(pieces != null) return pieces + " piece(s)";
        return weightPerPiece.intValue() + " kg";
    }

    public static String fmt(double value) {
        return String.format("%.2f", value);
    }

    private double orZero(Double value) {
        return value != null ? value : 0.0;
    }

    private String buildSubject(BookingConfirmedEvent event) {
        String departureDate = event.getDepartureDateTime() != null ? DATE_FMT.format(event.getDepartureDateTime()) : "";
        return String.format("Booking confirmed for | %s | %s\u2192%s | %s",
                event.getBookingReference(), event.getDepartureAirportCode(), event.getArrivalAirportCode(), departureDate);
    }

}
