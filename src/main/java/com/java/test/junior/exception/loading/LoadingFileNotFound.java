package com.java.test.junior.exception.loading;

public class LoadingFileNotFound extends RuntimeException {
    public LoadingFileNotFound(String s) {
        super(s);
    }
}
