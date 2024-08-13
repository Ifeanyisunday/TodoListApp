package com.TaskApp.service;

import com.TaskApp.data.model.User;
import com.TaskApp.data.repository.UserRepository;
import com.TaskApp.dtos.requests.LogOutRequest;
import com.TaskApp.dtos.requests.LoginRequest;
import com.TaskApp.dtos.requests.RegisterRequest;
import com.TaskApp.dtos.responses.LogOutResponse;
import com.TaskApp.dtos.responses.LoginResponse;
import com.TaskApp.dtos.responses.RegisterResponses;
import com.TaskApp.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public RegisterResponses registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        userRegisterValidation(user);
        userRepository.save(user);
        RegisterResponses registerResponses = new RegisterResponses();
        registerResponses.setFirstName(user.getFirstName());
        registerResponses.setMessage("Registration successful");
        return registerResponses;
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        userLoginValidation(loginRequest);
        Optional<User> userOptional = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setLoggedIn(true);
            userRepository.save(user);
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setFirstName(userOptional.get().getFirstName());
        loginResponse.setMessage("you are logged in");
        return loginResponse;
    }

    @Override
    public LogOutResponse logOutUser(LogOutRequest logOutRequest) {
        userLogOutValidation(logOutRequest);
        Optional<User> userOptional = userRepository.findByEmailAndPassword(logOutRequest.getEmail(), logOutRequest.getPassword());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setLoggedIn(false);
            userRepository.save(user);
        }
        LogOutResponse logOutResponse = new LogOutResponse();
        logOutResponse.setFirstName(userOptional.get().getFirstName());
        logOutResponse.setMessage("you are logged out");
        return logOutResponse;
    }




    private void userRegisterValidation(User user){
        if(user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            throw new UserDetailsEmpty("one field is empty");
        }

        if(user.getFirstName().equals(" ") || user.getLastName().equals(" ") || user.getEmail().equals(" ") || user.getPassword().equals(" ")) {
            throw new EmptySpaceException("Can not take white spaces");
        }

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()){
            throw new UserIsPresentException("User already exist");
        }
    }


    private void userLoginValidation(LoginRequest loginRequest) {
        if(loginRequest.getEmail().isEmpty() || loginRequest.getPassword().isBlank()){
            throw new UserLoginException("one field is empty");
        }

        if(loginRequest.getEmail().equals(" ") || loginRequest.getPassword().equals(" ")){
            throw new EmptySpaceLoginExeption("Can not take white spaces");
        }
    }

    private void userLogOutValidation(LogOutRequest logOutRequest) {
        if(logOutRequest.getEmail().isEmpty() || logOutRequest.getPassword().isEmpty()){
            throw new UserLoginException("one field is empty");
        }

        if(logOutRequest.getEmail().equals(" ") || logOutRequest.getPassword().equals(" ")){
            throw new EmptySpaceLoginExeption("Can not take white spaces");
        }
    }
}

