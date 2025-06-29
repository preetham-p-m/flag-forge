package com.pmp.flag_forge.Seed;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.pmp.flag_forge.Seed.Loader.FeatureFlagLoader;
import com.pmp.flag_forge.Service.FeatureFlagService;

import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class FeatureFlagSeeder implements ApplicationRunner {
    private final FeatureFlagLoader featureFlagLoader;
    private final FeatureFlagService featureFlagService;
    private final Validator validator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var flagDefinitions = featureFlagLoader.loadFlagDefinitions();

        for (var flagDefinition : flagDefinitions) {
            var violations = this.validator.validate(flagDefinition);
            if (!violations.isEmpty()) {
                violations.forEach(
                        v -> log.error("Validation failed for field '{}': {}", v.getPropertyPath(), v.getMessage()));
                continue;
            }

            if (featureFlagService.doesExistsByFlagKey(flagDefinition.getFlagKey())) {
                featureFlagService.patchUpdate(flagDefinition.getFlagKey(), flagDefinition);
            } else {
                featureFlagService.create(flagDefinition);
            }
        }
    }

}
