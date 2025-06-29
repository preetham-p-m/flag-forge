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
import com.pmp.flag_forge.Exception.Error.FlagForgeError;
import com.pmp.flag_forge.Exception.Error.FlagForgeNotFoundException;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final AppConfiguration appConfiguration;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<FlagForgeError> handleAllException(Exception ex,
            WebRequest request) throws Exception {
        FlagForgeError error = getResponse(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({ NotFound.class, FlagForgeNotFoundException.class })
    public final ResponseEntity<FlagForgeError> handleFlagNotFoundException(Exception ex,
            WebRequest request) {
        FlagForgeError error = getResponse(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private FlagForgeError getResponse(Exception ex) {
        var environment = this.appConfiguration.getGeneral().getEnvironment();

        if (environment.equals(Environment.DEVELOPMENT)) {
            return new FlagForgeError(ex.getMessage(), ex.getCause());
        }

        return new FlagForgeError(ex.getMessage());
    }

}
