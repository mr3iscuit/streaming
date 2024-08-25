package org.streaming.exception;
public class ResourceValidationException extends RuntimeException {
    public ResourceValidationException() {
        super("Required fields are missing.");
    }

    public ResourceValidationException(String message) {
        super(message);
    }
}
