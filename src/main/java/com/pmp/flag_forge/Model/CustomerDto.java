package com.pmp.flag_forge.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty
    @Size(min = 3, max = 255, message = "Customer Name length must be between 3 and 255 characters")
    private String customerName;
}
