package com.java.test.junior.exception.loading;

import java.sql.SQLException;

public class InvalidFileStructureException extends RuntimeException {
    public InvalidFileStructureException(String s) {
        super(s);
    }
}
