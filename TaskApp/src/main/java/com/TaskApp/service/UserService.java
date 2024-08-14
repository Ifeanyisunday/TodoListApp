package com.TaskApp.service;

import com.TaskApp.dtos.requests.*;
import com.TaskApp.dtos.responses.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    RegisterResponses registerUser(RegisterRequest registerRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    LogOutResponse logOutUser(LogOutRequest logOutRequest);
    DeleteProfileResponse deleteUser(DeleteUserRequest deleteUserRequest);
//    UpdateTaskResponse updateUserProfile(UpdateUserRequest updateUserRequest, RegisterRequest registerRequest);
}
