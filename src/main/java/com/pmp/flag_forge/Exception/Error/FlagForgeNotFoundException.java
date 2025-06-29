package com.pmp.flag_forge.Exception.Error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlagForgeNotFoundException extends RuntimeException {

    public FlagForgeNotFoundException(String message) {
        super(message);
    }

}
