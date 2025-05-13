package com.java.test.junior.exception.loading;

public class InvalidFileStructure extends RuntimeException {
    public InvalidFileStructure(String emptyOrInvalidFileContent) {
        super(emptyOrInvalidFileContent);
    }
}
