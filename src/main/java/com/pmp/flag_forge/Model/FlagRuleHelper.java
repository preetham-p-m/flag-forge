package com.pmp.flag_forge.Model;

public class FlagRuleHelper {

    public static FlagRule toEntity(FlagRuleDto dto, FeatureFlag featureFlag) {
        return FlagRule.builder()
                .featureFlag(featureFlag)
                .ruleType(dto.getRuleType())
                .ruleOperator(dto.getRuleOperator())
                .ruleValue(dto.getRuleValue())
                .targetValue(dto.getTargetValue())
                .build();
    }
}
