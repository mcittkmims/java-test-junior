package com.java.test.junior.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ProductException productException = new ProductException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(productException, notFound);
    }

    @ExceptionHandler(value = {PaginationParamsException.class})
    public ResponseEntity<Object> handlePaginationParamsException(PaginationParamsException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ProductException productException = new ProductException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(productException, badRequest);
    }

    @ExceptionHandler(value = {ProductCreationException.class, ProductDeletionException.class})
    public ResponseEntity<Object> handleProductCreationException(RuntimeException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ProductException productException = new ProductException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(productException, badRequest);
    }
}
