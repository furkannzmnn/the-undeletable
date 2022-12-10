package org.infa.exception;

public final class FileOpenException extends RuntimeException{
    public FileOpenException(String message) {
        super(message);
    }

    public FileOpenException(String message, Throwable cause) {
        super(message, cause);
    }
}
