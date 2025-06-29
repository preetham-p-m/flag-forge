package com.pmp.flag_forge.Exception.Error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlagNotFoundException extends RuntimeException {

    public FlagNotFoundException(String message) {
        super(message);
    }
}
