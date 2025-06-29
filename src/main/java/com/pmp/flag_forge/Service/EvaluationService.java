package com.pmp.flag_forge.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pmp.flag_forge.Exception.Error.FlagForgeException;
import com.pmp.flag_forge.Model.EvaluatedFlag;
import com.pmp.flag_forge.Model.FeatureFlag;
import com.pmp.flag_forge.Model.FlagRule;
import com.pmp.flag_forge.Model.FlagStatus;
import com.pmp.flag_forge.Model.RuleType;
import com.pmp.flag_forge.Model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationService {
    private final FlagRuleService flagRuleService;
    private final FeatureFlagService featureFlagService;
    private final UserService userService;

    public EvaluatedFlag getFlagsStatusByUserIdAndFlagId(UUID userId, UUID flagId) {
        var user = userService.getById(userId);
        var featureFlag = featureFlagService.getById(flagId);

        if (!featureFlag.getFlagStatus().equals(FlagStatus.ACTIVE)) {
            throw new FlagForgeException(
                    "Feature is not active for flag id " + featureFlag.getId() +
                            " and flagKey " + featureFlag.getFlagKey());
        }

        var userFlagRule = flagRuleService.findByRuleTypeAndRuleValueAndFeatureFlag_Id(
                RuleType.USER_ID, userId.toString(), flagId);
        if (userFlagRule.isPresent() &&
                Boolean.TRUE.equals(userFlagRule.get().getRuleEnabled())) {
            return createEvaluatedFlagFromFlagRule(userFlagRule.get(), user);
        }

        var customerFlagRule = flagRuleService.findByRuleTypeAndRuleValueAndFeatureFlag_Id(
                RuleType.CUSTOMER_ID, user.getCustomer().getId().toString(), flagId);
        if (customerFlagRule.isPresent() &&
                Boolean.TRUE.equals(customerFlagRule.get().getRuleEnabled())) {
            return createEvaluatedFlagFromFlagRule(customerFlagRule.get(), user);
        }

        return createEvaluatedFlagFromFeatureFlag(featureFlag, user);
    }

    private static EvaluatedFlag createEvaluatedFlagFromFlagRule(FlagRule flagRule, User user) {
        return EvaluatedFlag.builder()
                .customerId(user.getCustomer().getId())
                .userId(user.getId())
                .flagId(flagRule.getFeatureFlag().getId())
                .flagKey(flagRule.getFeatureFlag().getFlagKey())
                .flagValue(flagRule.getTargetValue())
                .build();
    }

    private static EvaluatedFlag createEvaluatedFlagFromFeatureFlag(FeatureFlag featureFlag, User user) {
        return EvaluatedFlag.builder()
                .customerId(user.getCustomer().getId())
                .userId(user.getId())
                .flagId(featureFlag.getId())
                .flagKey(featureFlag.getFlagKey())
                .flagValue(featureFlag.getDefaultValue())
                .build();
    }
}
