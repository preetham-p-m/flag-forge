package com.pmp.flag_forge.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmp.flag_forge.Model.FeatureFlag;
import com.pmp.flag_forge.Model.FlagDefinition;
import com.pmp.flag_forge.Service.FeatureFlagService;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/feature-flags")
public class FeatureFlagController {
    private final FeatureFlagService featureFlagService;

    public FeatureFlagController(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlag> getById(@PathVariable UUID id) {
        var featureFlag = this.featureFlagService.getById(id);
        return ResponseEntity.ok(featureFlag);
    }

    @GetMapping("/flag-key/{flagKey}")
    public ResponseEntity<FeatureFlag> getByFlagKey(@PathVariable String flagKey) {
        var featureFlag = this.featureFlagService.getByFlagKey(flagKey);
        return ResponseEntity.ok(featureFlag);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FeatureFlag> patchUpdate(@PathVariable UUID id,
            @RequestBody FlagDefinition flagDefinition) {
        var featureFlag = featureFlagService.patchUpdate(id, flagDefinition);
        return ResponseEntity.ok(featureFlag);
    }
}
