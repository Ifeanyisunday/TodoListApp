package com.TaskApp.service;

import com.TaskApp.data.model.Task;
import com.TaskApp.dtos.requests.CompleteTaskRequest;
import com.TaskApp.dtos.requests.FindAllRequest;
import com.TaskApp.dtos.requests.TaskAddRequest;
import com.TaskApp.dtos.responses.CompleteTaskResponse;
import com.TaskApp.dtos.responses.DeleteResponse;
import com.TaskApp.dtos.responses.TaskAddResponse;
import com.TaskApp.dtos.responses.UpdateTaskResponse;
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
