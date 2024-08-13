package com.TaskApp.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskAddResponse addTask(TaskAddRequest taskAddRequest);
    String findAll(FindAllRequest findAllRequest);
    CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest);
    String dailyTaskIsComplete(CompleteTaskRequest completeTaskRequest);
    UpdateTaskResponse updateTask(CompleteTaskRequest completeTaskRequest, TaskAddRequest taskAddRequest);
    DeleteResponse deleteTask(CompleteTaskRequest completeTaskRequest);
    List<Task> apiFindAll(CompleteTaskRequest completeTaskRequest);
}
