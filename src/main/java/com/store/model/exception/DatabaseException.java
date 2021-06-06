package com.store.model.exception;

public class DatabaseException extends Exception {
    public DatabaseException(String errorMessage) {
        super(errorMessage);
    }

    public DatabaseException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
