package com.java.test.junior.exception.user;

import com.java.test.junior.exception.product.ProductException;
import com.java.test.junior.exception.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class UsernameExceptionHandler {
    @ExceptionHandler(value = {UsernameTakenException.class})
    public ResponseEntity<Object> handleUsernameTakenException(UsernameTakenException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        UserException userException = new UserException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(userException, badRequest);
    }
}
