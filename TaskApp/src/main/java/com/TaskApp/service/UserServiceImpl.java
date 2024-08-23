package com.TaskApp.service;

import com.TaskApp.data.model.User;
import com.TaskApp.data.repository.UserRepository;
import com.TaskApp.dtos.requests.*;
import com.TaskApp.dtos.responses.*;
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
        userRegisterValidation(registerRequest);
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);
        RegisterResponses registerResponses = new RegisterResponses();
        String name = user.getUserName();
        registerResponses.setMessage(name + " your registration was successful");
        return registerResponses;
    }


    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        userLoginValidation(loginRequest);
        Optional<User> userOptional = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if(user.isLoggedIn() == false) {
                user.setLoggedIn(true);
                userRepository.save(user);
                LoginResponse loginResponse = new LoginResponse();
                String name = user.getUserName();
                loginResponse.setMessage(name + " you are logged in");
                return loginResponse;
            }else{
                LoginResponse loginResponse1 = new LoginResponse();
                String name = user.getUserName();
                loginResponse1.setMessage(name + " you are already logged in");
                return loginResponse1;
            }
        }
        return null;
    }

    @Override
    public LogOutResponse logOutUser(LogOutRequest logOutRequest) {
        userLogOutValidation(logOutRequest);
        Optional<User> userOptional = userRepository.findByEmail(logOutRequest.getEmail());
        User user;
        if (userOptional.isPresent()) {
            if(userOptional.get().isLoggedIn() == true) {
                userOptional.get().setLoggedIn(false);
                user = userOptional.get();
                userRepository.save(user);
                LogOutResponse logOutResponse = new LogOutResponse();
                String name = user.getUserName();
                logOutResponse.setMessage(name + " you are logged out");
                return logOutResponse;
            }else{
                user = userOptional.get();
                LogOutResponse logOutResponse = new LogOutResponse();
                String name = user.getUserName();
                logOutResponse.setMessage(name + " you are offline");
                return logOutResponse;
            }
        }
        return null;
    }

    public DeleteProfileResponse deleteUser(DeleteUserRequest deleteUserRequest){
        Optional<User> userOptional = userRepository.findByEmail(deleteUserRequest.getEmail());
        User user;
        if(userOptional.isPresent()){
            if(userOptional.get().isLoggedIn() == true) {
                user = userOptional.get();
                userRepository.delete(user);
                DeleteProfileResponse deleteProfileResponse = new DeleteProfileResponse();
                deleteProfileResponse.setMessage("Profile deleted");
                return deleteProfileResponse;
            }else{
                DeleteProfileResponse deleteProfileResponse = new DeleteProfileResponse();
                deleteProfileResponse.setMessage("you are offline, login to delete or user already deleted");
                return deleteProfileResponse;
            }
        }
        return null;
    }

//    public UpdateTaskResponse updateUserProfile(UpdateUserRequest updateUserRequest, RegisterRequest registerRequest){
//        Optional<User> userOptional = userRepository.findByUserName(updateUserRequest.getUserName());
//        if(userOptional.isPresent()){
//            if(userOptional.get().isLoggedIn() == true) {
//                User user = userOptional.get();
//                user.setUserName(registerRequest.getUserName());
//                user.setEmail(registerRequest.getEmail());
//                user.setPassword(registerRequest.getPassword());
//                userRepository.save(user);
//                UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
//                updateTaskResponse.setMessage("User profile updated");
//                return updateTaskResponse;
//            }
//        }
//        return null;
//    }


    private void userRegisterValidation(RegisterRequest registerRequest){
        if(registerRequest.getUserName().isEmpty() || registerRequest.getEmail().isEmpty() || registerRequest.getPassword().isEmpty()){
            throw new UserDetailsEmpty("one field is empty");
        }

        if(registerRequest.getUserName().equals(" ") || registerRequest.getEmail().equals(" ") || registerRequest.getPassword().equals(" ")) {
            throw new EmptySpaceException("Can not take white spaces");
        }

//        Optional<User> userOptional = userRepository.findByEmail(registerRequest.getEmail());
        for(User user : userRepository.findAll()){
            if(user.getUserName().equals(registerRequest.getUserName()) || user.getEmail().equals(registerRequest.getEmail()) || user.getPassword().equals(registerRequest.getPassword())){
                throw new UserIsPresentException("user already exist");
            }
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
        if(logOutRequest.getEmail().isEmpty()){
            throw new UserLoginException("one field is empty");
        }

        if(logOutRequest.getEmail().equals(" ")){
            throw new EmptySpaceLoginExeption("Can not take white spaces");
        }
    }
}

