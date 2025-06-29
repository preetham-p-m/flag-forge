package com.pmp.flag_forge.Model.FlagRule;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlagRuleDto {

    @NotNull
    private UUID featureFlagId;

    @NotNull
    private RuleType ruleType;

    @NotNull
    private RuleOperator ruleOperator;

    @NotEmpty
    private String ruleValue;

    @NotNull
    private Boolean targetValue;
}
