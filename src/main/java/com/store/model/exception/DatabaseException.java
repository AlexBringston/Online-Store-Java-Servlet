package com.store.model.exception;

/**
 * Custom exception class which is used to display wanted messages and hiding the exact exception which caused the
 * problem. Implements two super constructors from super class.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class DatabaseException extends Exception {
    public DatabaseException(String errorMessage) {
        super(errorMessage);
    }

    public DatabaseException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
