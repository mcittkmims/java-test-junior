package com.java.test.junior.exception.global;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        GlobalException globalException = new GlobalException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(globalException, badRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        String errorMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        GlobalException globalException = new GlobalException(
                errorMessage,
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );

        return new ResponseEntity<>(globalException, badRequest);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        GlobalException globalException = new GlobalException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(globalException, badRequest);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleJwtValidationException(HttpServletRequest request, Exception ex) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;

        GlobalException globalException = new GlobalException(
                ex.getMessage(),
                forbidden,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(globalException, forbidden);
    }
}
