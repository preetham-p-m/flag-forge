package com.pmp.flag_forge.Model;

import java.util.List;

import com.pmp.flag_forge.Model.FeatureFlag.FeatureFlagDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlagDefinitionWrapper {

    private List<FeatureFlagDto> flags;
}
