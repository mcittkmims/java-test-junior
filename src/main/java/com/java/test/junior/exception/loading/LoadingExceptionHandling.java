package com.java.test.junior.exception.loading;

import com.java.test.junior.exception.user.UserException;
import com.java.test.junior.exception.user.UsernameTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class LoadingExceptionHandling {
    @ExceptionHandler(value = {LoadingFileNotFound.class})
    public ResponseEntity<Object> handleLoadingFileNotFoundException(LoadingFileNotFound e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        LoadingException loadingException = new LoadingException(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(loadingException, status);
    }

    @ExceptionHandler(value = {InvalidFileStructure.class})
    public ResponseEntity<Object> handleLoadingInvalidFileStructure(InvalidFileStructure e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        LoadingException loadingException = new LoadingException(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(loadingException, status);
    }
}
