package com.learning.pricing.service.mapper;

import com.learning.common.payload.request.FareRuleRequest;
import com.learning.common.payload.response.FareRuleResponse;
import com.learning.pricing.service.model.Fare;
import com.learning.pricing.service.model.FareRule;

public class FareRuleMapper {

    public static FareRuleResponse toFareRule(FareRule fareRule) {
        if(fareRule == null) return null;

        return FareRuleResponse.builder()
                .id(fareRule.getId())
                .fareId(fareRule.getFare().getId())
                .airlineId(fareRule.getAirlineId())
                .ruleName(fareRule.getRuleName())
                .isRefundable(fareRule.getIsRefundable())
                .changeFee(fareRule.getChangeFee())
                .cancellationFee(fareRule.getCancellationFee())
                .refundDeadlineDays(fareRule.getRefundDeadlineDays())
                .changeDeadlineHours(fareRule.getChangeDeadlineHours())
                .isChangeable(fareRule.getIsChangeable())
                .createdAt(fareRule.getCreatedAt())
                .updatedAt(fareRule.getUpdatedAt())
                .build();
    }

    public static FareRule toFareRule(FareRuleRequest request, Fare fare) {
        if(request == null) return null;
        return FareRule.builder()
                .ruleName(request.getRuleName())
                .fare(fare)
                .airlineId(request.getAirlineId())
                .isRefundable(request.getIsRefundable())
                .changeFee(request.getChangeFee())
                .cancellationFee(request.getCancellationFee())
                .refundDeadlineDays(request.getRefundDeadlineDays())
                .changeDeadlineHours(request.getChangeDeadlineHours())
                .isChangeable(request.getIsChangeable() != null ? request.getIsChangeable() : false)
                .build();
    }

    public static void toFareRule(FareRule fareRule, FareRuleRequest request) {
        if(fareRule == null || request == null) return;

        fareRule.setRuleName(request.getRuleName());
        fareRule.setAirlineId(request.getAirlineId());
        fareRule.setIsRefundable(request.getIsRefundable());
        fareRule.setChangeFee(request.getChangeFee());
        fareRule.setCancellationFee(request.getCancellationFee());
        fareRule.setRefundDeadlineDays(request.getRefundDeadlineDays());
        fareRule.setChangeDeadlineHours(request.getChangeDeadlineHours());
        fareRule.setIsChangeable(request.getIsChangeable());
    }
}
