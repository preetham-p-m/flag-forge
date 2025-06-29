package com.pmp.flag_forge.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmp.flag_forge.Model.FlagRule;
import com.pmp.flag_forge.Model.RuleType;

public interface FlagRuleRepository extends JpaRepository<FlagRule, UUID> {

    Optional<FlagRule> findByRuleTypeAndRuleValueAndFeatureFlag_Id(
            RuleType ruleType,
            String ruleValue,
            UUID featureFlagId);
}
