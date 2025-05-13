package com.java.test.junior.exception.global;

import com.java.test.junior.filter.JWTFilter;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
    public ResponseEntity<Object> handleJwtValidationException(HttpServletRequest request, JwtException ex) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;

        GlobalException globalException = new GlobalException(
                ex.getMessage(),
                forbidden,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(globalException, forbidden);
    }

//    @ExceptionHandler(SQLException.class)
//    public ResponseEntity<Object> handleForeignKeySQLException(SQLException e) throws SQLException{
//        if ("23503".equals(e.getSQLState())) {
//            HttpStatus status = HttpStatus.BAD_REQUEST;
//            GlobalException globalException = new GlobalException(
//                    "Invalid data",
//                    status,
//                    ZonedDateTime.now(ZoneId.of("UTC"))
//            );
//            return new ResponseEntity<>(globalException, status);
//        }
//
//        throw e;
//    }
}
