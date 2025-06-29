package com.pmp.flag_forge.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.pmp.flag_forge.Exception.Error.FlagForgeNotFoundException;
import com.pmp.flag_forge.Model.FlagRule;
import com.pmp.flag_forge.Model.FlagRuleDto;
import com.pmp.flag_forge.Model.FlagRuleHelper;
import com.pmp.flag_forge.Model.RuleType;
import com.pmp.flag_forge.Repository.FlagRuleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FlagRuleService {
    private final FlagRuleRepository flagRuleRepository;
    private final FeatureFlagService featureFlagService;

    public FlagRule create(FlagRuleDto flagRuleDto) {
        var featureFlag = featureFlagService.getById(flagRuleDto.getFeatureFlagId());
        var flagRule = FlagRuleHelper.toEntity(flagRuleDto, featureFlag);
        return flagRuleRepository.save(flagRule);
    }

    public void deleteById(UUID id) {
        flagRuleRepository.deleteById(id);
    }

    public FlagRule getById(UUID id) {
        return flagRuleRepository.findById(id)
                .orElseThrow(() -> new FlagForgeNotFoundException("Flag Rule not found for id " + id));
    }

    public FlagRule toggleById(UUID id) {
        var flagRule = this.getById(id);

        var newStatus = flagRule.getRuleEnabled() == true ? false : true;
        flagRule.setRuleEnabled(newStatus);

        return this.flagRuleRepository.save(flagRule);
    }

    public Optional<FlagRule> findByRuleTypeAndRuleValueAndFeatureFlag_Id(
            RuleType ruleType, String ruleValue, UUID flagId) {
        return flagRuleRepository.findByRuleTypeAndRuleValueAndFeatureFlag_Id(
                ruleType, ruleValue, flagId);
    }
}
