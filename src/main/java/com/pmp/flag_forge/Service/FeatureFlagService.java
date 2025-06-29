package com.pmp.flag_forge.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pmp.flag_forge.Exception.Error.FlagForgeNotFoundException;
import com.pmp.flag_forge.Model.FeatureFlag;
import com.pmp.flag_forge.Model.FeatureFlagDto;
import com.pmp.flag_forge.Repository.FeatureFlagRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeatureFlagService {
    private final FeatureFlagRepository featureFlagRepository;

    public FeatureFlag create(FeatureFlagDto def) {
        FeatureFlag newFlag = FeatureFlag.builder()
                .flagKey(def.getFlagKey())
                .name(def.getName())
                .description(def.getDescription())
                .defaultValue(def.getDefaultValue())
                .managedBy(def.getManagedBy())
                .build();

        return this.featureFlagRepository.save(newFlag);
    }

    public boolean doesExistsByFlagKey(String flagKey) {
        return featureFlagRepository.existsByFlagKey(flagKey);
    }

    public FeatureFlag getById(UUID id) {
        return featureFlagRepository.findById(id)
                .orElseThrow(() -> new FlagForgeNotFoundException("Feature flag not found for id " + id));
    }

    public FeatureFlag getByFlagKey(String flagKey) {
        return featureFlagRepository.findByFlagKey(
                flagKey)
                .orElseThrow(() -> new FlagForgeNotFoundException("Feature flag not found for flag key " + flagKey));
    }

    public FeatureFlag patchUpdate(UUID id, FeatureFlagDto flagDefinition) {
        var featureFlag = this.getById(id);
        return patchUpdateInternal(featureFlag, flagDefinition);
    }

    public FeatureFlag patchUpdate(String flagKey, FeatureFlagDto flagDefinition) {
        var featureFlag = this.getByFlagKey(flagKey);
        return patchUpdateInternal(featureFlag, flagDefinition);
    }

    public FeatureFlag toggleById(UUID id) {
        var featureFlag = this.getById(id);
        return toggleFlagInternal(featureFlag);
    }

    public FeatureFlag toggleByFlagKey(String flagKey) {
        var featureFlag = this.getByFlagKey(flagKey);
        return toggleFlagInternal(featureFlag);
    }

    private FeatureFlag toggleFlagInternal(FeatureFlag featureFlag) {
        var newValue = featureFlag.getDefaultValue() == true ? false : true;
        featureFlag.setDefaultValue(newValue);
        return this.featureFlagRepository.save(featureFlag);
    }

    private FeatureFlag patchUpdateInternal(FeatureFlag featureFlag, FeatureFlagDto flagDefinition) {

        boolean hasChanges = false;

        if (flagDefinition.getName() != null && !flagDefinition.getName().equals(featureFlag.getName())) {
            featureFlag.setName(flagDefinition.getName());
            hasChanges = true;
        }

        if (flagDefinition.getDescription() != null
                && !flagDefinition.getDescription().equals(featureFlag.getDescription())) {
            featureFlag.setDescription(flagDefinition.getDescription());
            hasChanges = true;
        }

        if (flagDefinition.getManagedBy() != null
                && !flagDefinition.getManagedBy().equals(featureFlag.getManagedBy())) {
            featureFlag.setManagedBy(flagDefinition.getManagedBy());
            hasChanges = true;
        }

        if (flagDefinition.getDefaultValue() != null
                && !flagDefinition.getDefaultValue().equals(featureFlag.getDefaultValue())) {
            featureFlag.setDefaultValue(flagDefinition.getDefaultValue());
            hasChanges = true;
        }

        return hasChanges ? featureFlagRepository.save(featureFlag) : featureFlag;
    }

}
