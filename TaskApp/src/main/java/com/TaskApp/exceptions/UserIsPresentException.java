package com.TaskApp.exceptions;

public class UserIsPresentException extends RuntimeException {
    public UserIsPresentException(String message) {
        super(message);
    }
}
