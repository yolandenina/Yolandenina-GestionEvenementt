package com.evenement.gestionevenement.exception;

import com.evenement.gestionevenement.utils.TextUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        String errorMessage = getMessage(ex.getMessage(), null, request.getLocale());
        ErrorDetails errorDetails = new ErrorDetails(new Date(),errorMessage, LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    public String getMessage(String code, Map<String, Object> args, Locale locale) {
        String message = messageSource.getMessage(code, null, code, locale);
        return TextUtils.format(message, args);
    }

}
