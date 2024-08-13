package com.TaskApp.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    RegisterResponses registerUser(RegisterRequest registerRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    LogOutResponse logOutUser(LogOutRequest logOutRequest);
}
