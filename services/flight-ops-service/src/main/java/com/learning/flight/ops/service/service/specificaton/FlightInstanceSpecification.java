package com.learning.flight.ops.service.service.specificaton;

import com.learning.common.enums.FlightStatus;
import com.learning.common.payload.request.FlightSearchRequest;
import com.learning.flight.ops.service.model.FlightInstance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.learning.common.enums.FlightStatus.CANCELLED;
import static com.learning.common.enums.FlightStatus.COMPLETED;
import static com.learning.common.enums.FlightStatus.DIVERTED;

public class FlightInstanceSpecification {

    private static final Set<FlightStatus> EXCLUDED_STATUS = Set.of(CANCELLED, COMPLETED, DIVERTED);

    private FlightInstanceSpecification() {}

    public static Specification<FlightInstance> buildSpecification(FlightSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));
            predicates.add(root.get("status").in(EXCLUDED_STATUS).not());
            predicates.add(criteriaBuilder.greaterThan(root.get("departureDateTime"), LocalDateTime.now()));
            predicates.add(criteriaBuilder.equal(root.get("departureAirportId"), request.getDepartureAirportId()));
            predicates.add(criteriaBuilder.equal(root.get("arrivalAirportId"), request.getArrivalAirportId()));

            LocalDateTime startOfDate = request.getDepartureDate().atStartOfDay();
            LocalDateTime endOfDate = request.getDepartureDate().atTime(LocalTime.MAX);
            predicates.add(criteriaBuilder.between(root.get("departureDateTime"), startOfDate, endOfDate));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("availableSeats"), request.getPassengers()));

            if(request.getAirlines() != null && !request.getAirlines().isEmpty()) {
                predicates.add(root.get("airlineId").in(request.getAirlines()));
            }

            if(isFilterableTimeRange(request.getDepartureTimeRange())) {
                applyTimeRangePredicate(predicates, root, criteriaBuilder, "departureDateTime", request.getDepartureTimeRange());
            }

            if(isFilterableTimeRange(request.getArrivalTimeRange())) {
                applyTimeRangePredicate(predicates, root, criteriaBuilder, "arrivalDateTime", request.getArrivalTimeRange());
            }

            if(request.getMaxDuration() != null) {
                Expression<Integer> durationMinutes = criteriaBuilder.function("TIMESTAMPDIFF", Integer.class,
                        criteriaBuilder.literal("MINUTE"), root.get("departureDateTime"), root.get("arrivalDateTime"));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(durationMinutes, request.getMaxDuration()));
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean isFilterableTimeRange(String range) {
        return range != null && range.isBlank() && !range.equalsIgnoreCase("any");
    }

    private static void applyTimeRangePredicate(
            List<Predicate> predicates,
            Root<FlightInstance> root,
            CriteriaBuilder criteriaBuilder,
            String dateTimeField,
            String timeRange
    ) {
        Expression<Integer> hours = criteriaBuilder.function("HOUR", Integer.class, root.get(dateTimeField));
        switch (timeRange.toLowerCase()) {
            case "morning": predicates.add(criteriaBuilder.between(hours, 6, 11));
            case "afternoon": predicates.add(criteriaBuilder.between(hours, 12, 17));
            case "evening": predicates.add(criteriaBuilder.between(hours, 18, 20));
            case "night": predicates.add(criteriaBuilder.or(
                    criteriaBuilder.greaterThanOrEqualTo(hours, 21),
                    criteriaBuilder.lessThanOrEqualTo(hours, 5))
            );
            default: {}
        }

    }
}
