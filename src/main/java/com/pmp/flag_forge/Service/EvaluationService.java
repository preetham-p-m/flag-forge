package com.pmp.flag_forge.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<EvaluatedFlag> evaluateByUserIdAndFlagKeys(UUID userId, List<String> flagKeys) {
        var featureFlags = featureFlagService.getByFlagKeys(flagKeys);

        var inActiveFlags = featureFlags.stream()
                .filter(x -> !x.isActive())
                .map(FeatureFlag::getFlagKey)
                .collect(Collectors.toList());

        if (!inActiveFlags.isEmpty()) {
            throw new FlagForgeException("The following Feature flag not active: " +
                    String.join(", ", inActiveFlags));
        }

        var featureFlagMap = featureFlags.stream()
                .collect(Collectors.toMap(FeatureFlag::getId, ff -> ff));
        var neededFeatureFlagsId = new HashSet<>(featureFlagMap.keySet());

        var user = userService.getById(userId);

        List<EvaluatedFlag> evaluatedResult = new ArrayList<>();

        // User evaluation
        var userOverrides = flagRuleService.findByRuleTypeAndRuleValueAndFeatureFlag_IdIn(
                RuleType.USER_ID,
                userId.toString(),
                new ArrayList<>(neededFeatureFlagsId));

        userOverrides.forEach(uo -> {
            FeatureFlag ff = featureFlagMap.get(uo.getFeatureFlag().getId());
            evaluatedResult.add(createEvaluatedFlagFromRule(user, ff, uo));
            neededFeatureFlagsId.remove(uo.getFeatureFlag().getId());
        });

        // Customer evaluation
        var customerOverrides = flagRuleService.findByRuleTypeAndRuleValueAndFeatureFlag_IdIn(
                RuleType.CUSTOMER_ID,
                user.getCustomer().getId().toString(),
                new ArrayList<>(neededFeatureFlagsId));

        customerOverrides.forEach(co -> {
            FeatureFlag ff = featureFlagMap.get(co.getFeatureFlag().getId());
            evaluatedResult.add(createEvaluatedFlagFromRule(user, ff, co));
            neededFeatureFlagsId.remove(co.getFeatureFlag().getId());
        });

        // Add defaults for flags without user overrides
        neededFeatureFlagsId.forEach(flagId -> {
            FeatureFlag ff = featureFlagMap.get(flagId);
            evaluatedResult.add(createEvaluatedFlagFromDefault(user, ff));
        });

        return evaluatedResult;
    }

    public EvaluatedFlag evaluateFlagForUserInternal(UUID userId, FeatureFlag featureFlag) {
        if (!featureFlag.isActive()) {
            throw new FlagForgeException(
                    "Feature is not active for flag id " + featureFlag.getId() +
                            " and flagKey " + featureFlag.getFlagKey());
        }

        var user = userService.getById(userId);

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
