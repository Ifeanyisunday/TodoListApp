package com.TaskApp.dtos.requests;

import lombok.Data;

@Data
public class RegisterRequest {

    private String userName;
    private String email;
    private String password;
}