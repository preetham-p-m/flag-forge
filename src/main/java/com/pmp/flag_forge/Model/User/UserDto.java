package com.pmp.flag_forge.Model.User;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty
    @Size(min = 5, max = 100, message = "Username length must be between 5 and 100 characters")
    private String userName;

    @NotEmpty
    private String fullName;

    @NotNull
    private UUID customerId;
}
