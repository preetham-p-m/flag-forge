package com.pmp.flag_forge.Model;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Setter
@Getter
public class FlagDefinition {

    @NotEmpty
    @Size(min = 5, max = 256, message = "FlagKey must be between 5 and 256 characters")
    @Pattern(regexp = "^[a-z0-9._-]+$", message = "Flag key must be lowercase and contain only '.', '_', or '-'")
    private String flagKey;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private Boolean defaultValue;

    @NotEmpty
    private String managedBy;
}
