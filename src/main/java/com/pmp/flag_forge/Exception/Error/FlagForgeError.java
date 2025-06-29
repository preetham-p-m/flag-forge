package com.pmp.flag_forge.Exception.Error;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FlagForgeError {

    private final String message;
    private final LocalDateTime timeStamp;
    private final Throwable cause;

    public FlagForgeError(String message) {
        this.message = message;
        this.cause = null;
        this.timeStamp = LocalDateTime.now();
    }

    public FlagForgeError(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
        this.timeStamp = LocalDateTime.now();
    }

}