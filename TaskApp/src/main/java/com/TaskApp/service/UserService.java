package com.TaskApp.service;

import com.TaskApp.dtos.requests.LogOutRequest;
import com.TaskApp.dtos.requests.LoginRequest;
import com.TaskApp.dtos.requests.RegisterRequest;
import com.TaskApp.dtos.responses.LogOutResponse;
import com.TaskApp.dtos.responses.LoginResponse;
import com.TaskApp.dtos.responses.RegisterResponses;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    RegisterResponses registerUser(RegisterRequest registerRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    LogOutResponse logOutUser(LogOutRequest logOutRequest);
}
