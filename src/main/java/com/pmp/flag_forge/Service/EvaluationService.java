package com.pmp.flag_forge.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pmp.flag_forge.Exception.Error.FlagForgeException;
import com.pmp.flag_forge.Model.EvaluatedFlag;
import com.pmp.flag_forge.Model.FeatureFlag.FeatureFlag;
import com.pmp.flag_forge.Model.FeatureFlag.FlagStatus;
import com.pmp.flag_forge.Model.FlagRule.FlagRule;
import com.pmp.flag_forge.Model.FlagRule.RuleType;
import com.pmp.flag_forge.Model.User.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationService {
    private final FlagRuleService flagRuleService;
    private final FeatureFlagService featureFlagService;
    private final UserService userService;

    public EvaluatedFlag evaluateByUserIdAndFlagKey(UUID userId, String flagKey) {
        var featureFlag = featureFlagService.getByFlagKey(flagKey);
        return evaluateFlagForUserInternal(userId, featureFlag);
    }

    public EvaluatedFlag evaluateByUserIdAndFlagId(UUID userId, UUID flagId) {
        var featureFlag = featureFlagService.getById(flagId);
        return evaluateFlagForUserInternal(userId, featureFlag);
    }

    public EvaluatedFlag evaluateFlagForUserInternal(UUID userId, FeatureFlag featureFlag) {
        var user = userService.getById(userId);

        if (!featureFlag.getFlagStatus().equals(FlagStatus.ACTIVE)) {
            throw new FlagForgeException(
                    "Feature is not active for flag id " + featureFlag.getId() +
                            " and flagKey " + featureFlag.getFlagKey());
        }

        var userFlagRule = flagRuleService.findByRuleTypeAndRuleValueAndFeatureFlag_Id(
                RuleType.USER_ID, userId.toString(), featureFlag.getId());
        if (userFlagRule.isPresent() &&
                Boolean.TRUE.equals(userFlagRule.get().getRuleEnabled())) {
            return createEvaluatedFlagFromRule(user, featureFlag, userFlagRule.get());
        }

        var customerFlagRule = flagRuleService.findByRuleTypeAndRuleValueAndFeatureFlag_Id(
                RuleType.CUSTOMER_ID, user.getCustomer().getId().toString(), featureFlag.getId());
        if (customerFlagRule.isPresent() &&
                Boolean.TRUE.equals(customerFlagRule.get().getRuleEnabled())) {
            return createEvaluatedFlagFromRule(user, featureFlag, customerFlagRule.get());
        }

        return createEvaluatedFlagFromDefault(user, featureFlag);
    }

    private static EvaluatedFlag createEvaluatedFlagFromRule(
            User user, FeatureFlag featureFlag, FlagRule flagRule) {
        return EvaluatedFlag.builder()
                .customerId(user.getCustomer().getId())
                .userId(user.getId())
                .flagId(featureFlag.getId())
                .flagKey(featureFlag.getFlagKey())
                .flagValue(flagRule.getTargetValue())
                .build();
    }

    private static EvaluatedFlag createEvaluatedFlagFromDefault(
            User user, FeatureFlag featureFlag) {
        return EvaluatedFlag.builder()
                .customerId(user.getCustomer().getId())
                .userId(user.getId())
                .flagId(featureFlag.getId())
                .flagKey(featureFlag.getFlagKey())
                .flagValue(featureFlag.getDefaultValue())
                .build();
    }
}
