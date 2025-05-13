package com.java.test.junior.exception.loading;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException(String emptyOrInvalidFileContent) {
        super(emptyOrInvalidFileContent);
    }
}
