package com.pmp.flag_forge.Exception.Response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FlagForgeErrorResponse {

    private final String message;
    private final LocalDateTime timeStamp;
    private final Throwable cause;

    public FlagForgeErrorResponse(String message) {
        this.message = message;
        this.cause = null;
        this.timeStamp = LocalDateTime.now();
    }

    public FlagForgeErrorResponse(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
        this.timeStamp = LocalDateTime.now();
    }

}