package com.pmp.flag_forge.Exception.Error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlagForgeException extends RuntimeException {

    public FlagForgeException(String message) {
        super(message);
    }
}
