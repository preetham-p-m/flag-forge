package com.pmp.flag_forge.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pmp.flag_forge.Configuration.AppConfiguration;
import com.pmp.flag_forge.Constant.Environment;
import com.pmp.flag_forge.Exception.Error.FlagForgeNotFoundException;
import com.pmp.flag_forge.Exception.Response.FlagForgeErrorResponse;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final AppConfiguration appConfiguration;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<FlagForgeErrorResponse> handleAllException(Exception ex,
            WebRequest request) throws Exception {
        FlagForgeErrorResponse error = getResponse(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({ NotFound.class, FlagForgeNotFoundException.class })
    public final ResponseEntity<FlagForgeErrorResponse> handleFlagNotFoundException(Exception ex,
            WebRequest request) {
        FlagForgeErrorResponse error = getResponse(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private FlagForgeErrorResponse getResponse(Exception ex) {
        var environment = this.appConfiguration.getGeneral().getEnvironment();

        if (environment.equals(Environment.DEVELOPMENT)) {
            return new FlagForgeErrorResponse(ex.getMessage(), ex.getCause());
        }

        return new FlagForgeErrorResponse(ex.getMessage());
    }

}
