package com.pmp.flag_forge.Seed.Loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.pmp.flag_forge.Configuration.AppConfiguration;
import com.pmp.flag_forge.Model.FeatureFlagDto;
import com.pmp.flag_forge.Model.FlagDefinitionWrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class FeatureFlagLoader {
    private final AppConfiguration appConfiguration;

    public List<FeatureFlagDto> loadFlagDefinitions() {
        var currentEnvironment = appConfiguration.getGeneral().getEnvironment();
        var fileName = "flags-config-" + currentEnvironment + ".yaml";

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new IllegalArgumentException("Flag config file not found: " + fileName);
            }

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            FlagDefinitionWrapper wrapper = mapper.readValue(is, FlagDefinitionWrapper.class);

            List<FeatureFlagDto> flags = wrapper.getFlags();

            if (flags == null || flags.isEmpty()) {
                log.info("No feature flags found in: " + fileName);
                return new ArrayList<>();
            }

            return flags;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load flag config");
        }
    }
}
