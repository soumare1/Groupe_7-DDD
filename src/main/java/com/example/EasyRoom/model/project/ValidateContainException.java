package com.example.EasyRoom.model.project;



public class ValidateContainException extends RuntimeException {

    public ValidateContainException() {
        super();
    }
    public ValidateContainException(String message) {
        super(message);
    }

    public ValidateContainException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateContainException(Throwable cause) {
        super(cause);
    }
}
