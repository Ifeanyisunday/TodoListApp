package com.TaskApp.exceptions;

public class UserLoginException extends RuntimeException{
    public UserLoginException(String message) {
        super(message);
    }
}
