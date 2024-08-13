package com.TaskApp.exceptions;

public class UserDetailsEmpty extends RuntimeException{
    public UserDetailsEmpty(String message){
        super(message);
    }
}
