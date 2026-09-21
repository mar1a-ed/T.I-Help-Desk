package com.maria.help_desk.exception;

public class ClosedFeatureException extends RuntimeException {
    public ClosedFeatureException(String message) {
        super(message);
    }
}
