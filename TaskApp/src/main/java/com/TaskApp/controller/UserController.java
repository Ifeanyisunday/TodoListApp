package com.TaskApp.controller;


import com.TaskApp.dtos.requests.*;
import com.TaskApp.dtos.responses.ApiResponse;
import com.TaskApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

    @RestController
    @RequestMapping("api/v1/users")
    @CrossOrigin
    public class UserController {

        @Autowired
        private UserService userService;


        @PostMapping("/user")
        public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
            try {
                var result = userService.registerUser(registerRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }

        @PatchMapping("/loginUser")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            try {
                var result = userService.loginUser(loginRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }

        @PatchMapping("/logoutUser")
        public ResponseEntity<?> logOut(@RequestBody LogOutRequest logOutRequest) {
            try {
                var result = userService.logOutUser(logOutRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }

        @DeleteMapping("/deleteUser")
        public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest){
            try {
                var result = userService.deleteUser(deleteUserRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            }catch (Exception e){
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
            }
        }

//        @PatchMapping("/updateUser")
//        public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest, @RequestBody RegisterRequest registerRequest){
//            try {
//                var result = userService.updateUserProfile(updateUserRequest, registerRequest);
//                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
//            }catch (Exception e) {
//                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
//            }
//        }


    }

