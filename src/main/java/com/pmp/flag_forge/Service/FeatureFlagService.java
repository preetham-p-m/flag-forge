package com.pmp.flag_forge.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pmp.flag_forge.Model.FeatureFlag;
import com.pmp.flag_forge.Model.FlagDefinition;
import com.pmp.flag_forge.Repository.FeatureFlagRepository;

@Service
public class FeatureFlagService {

    private final FeatureFlagRepository featureFlagRepository;

    public FeatureFlagService(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    public FeatureFlag create(FlagDefinition def) {
        FeatureFlag newFlag = FeatureFlag.builder()
                .flagKey(def.getFlagKey())
                .name(def.getName())
                .description(def.getDescription())
                .defaultValue(def.getDefaultValue())
                .managedBy(def.getManagedBy())
                .build();

        return this.featureFlagRepository.save(newFlag);
    }

    public FeatureFlag patchUpdate(UUID id, FlagDefinition flagDefinition) {
        FeatureFlag featureFlag = featureFlagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature flag not found for id " + id));

        return patchUpdateInternal(featureFlag, flagDefinition);
    }

    public FeatureFlag patchUpdate(String flagKey, FlagDefinition flagDefinition) {
        FeatureFlag featureFlag = featureFlagRepository.findByFlagKey(flagKey)
                .orElseThrow(() -> new RuntimeException("Feature flag not found for id " + flagKey));

        return patchUpdateInternal(featureFlag, flagDefinition);
    }

    public boolean doesFlagExists(String flagKey) {
        return featureFlagRepository.existsByFlagKey(flagKey);
    }

    private FeatureFlag patchUpdateInternal(FeatureFlag featureFlag, FlagDefinition flagDefinition) {

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
