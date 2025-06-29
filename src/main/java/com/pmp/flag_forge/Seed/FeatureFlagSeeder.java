package com.pmp.flag_forge.Seed;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.pmp.flag_forge.Seed.Loader.FeatureFlagLoader;
import com.pmp.flag_forge.Service.FeatureFlagService;

@Component
public class FeatureFlagSeeder implements ApplicationRunner {

    private final FeatureFlagLoader featureFlagLoader;
    private final FeatureFlagService featureFlagService;

    public FeatureFlagSeeder(FeatureFlagLoader featureFlagLoader, FeatureFlagService featureFlagService) {
        this.featureFlagLoader = featureFlagLoader;
        this.featureFlagService = featureFlagService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var flagDefinitions = featureFlagLoader.loadFlagDefinitions();

        for (var flagDefinition : flagDefinitions) {
            if (featureFlagService.doesFlagExists(flagDefinition.getFlagKey())) {
                featureFlagService.patchUpdate(flagDefinition.getFlagKey(), flagDefinition);
            } else {
                featureFlagService.create(flagDefinition);
            }
        }
    }

}
