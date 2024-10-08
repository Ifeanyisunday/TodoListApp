package com.TaskApp.controller;

import com.TaskApp.dtos.requests.CompleteTaskRequest;
import com.TaskApp.dtos.requests.FindAllRequest;
import com.TaskApp.dtos.requests.TaskAddRequest;
import com.TaskApp.dtos.responses.ApiResponse;
import com.TaskApp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/task")
@CrossOrigin
public class TaskController {

        @Autowired
        private TaskService taskService;

        @PostMapping("/addTask")
        public ResponseEntity<?> addUserTask(@RequestBody TaskAddRequest taskAddRequest) {
            try {
                var result = taskService.addTask(taskAddRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @GetMapping("/getAllTask")
        public ResponseEntity<?> getAllTask(@RequestBody FindAllRequest findAllRequest) {
            try {
                var result = taskService.findAll(findAllRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @PatchMapping("/completeTask")
        public ResponseEntity<?> completeTask(@RequestBody CompleteTaskRequest completeTaskRequest) {
            try {
                var result = taskService.completeTask(completeTaskRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @GetMapping("/dailyTaskIsComplete")
        public ResponseEntity<?> dailyTaskIsComplete(@RequestBody CompleteTaskRequest completeTaskRequest) {
            try {
                var result = taskService.dailyTaskIsComplete(completeTaskRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }

        @DeleteMapping("/deleteTask")
        public ResponseEntity<?> deleteTask(@RequestBody CompleteTaskRequest completeTaskRequest) {
            try {
                var result = taskService.deleteTask(completeTaskRequest);
                return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),
                        BAD_REQUEST);
            }
        }


}
