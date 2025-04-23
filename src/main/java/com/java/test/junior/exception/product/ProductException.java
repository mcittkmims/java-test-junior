package com.java.test.junior.exception.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ProductException {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
}
