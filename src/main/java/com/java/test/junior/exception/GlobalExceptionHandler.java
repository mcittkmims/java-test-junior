package com.java.test.junior.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
}
