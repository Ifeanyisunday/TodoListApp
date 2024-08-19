package com.TaskApp.service;

import com.TaskApp.data.model.Task;
import com.TaskApp.data.model.User;
import com.TaskApp.data.repository.TaskRepository;
import com.TaskApp.data.repository.UserRepository;
import com.TaskApp.dtos.requests.CompleteTaskRequest;
import com.TaskApp.dtos.requests.FindAllRequest;
import com.TaskApp.dtos.requests.LoginRequest;
import com.TaskApp.dtos.requests.TaskAddRequest;
import com.TaskApp.dtos.responses.*;
import com.TaskApp.exceptions.EmptySpaceLoginExeption;
import com.TaskApp.exceptions.UserIsPresentException;
import com.TaskApp.exceptions.UserLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskAddResponse addTask(TaskAddRequest taskAddRequest) {
        addTaskValidation(taskAddRequest);
        Optional<User> userOptional = userRepository.findByEmail(taskAddRequest.getEmail());
        if (userOptional.isPresent()) {
            Optional<Task> taskOptional = taskRepository.findByTitle(taskAddRequest.getTitle());
            User user = userOptional.get();
            TaskAddResponse taskAddResponse = new TaskAddResponse();
            if (taskOptional.isPresent() && userOptional.get().isLoggedIn() == false) {
                taskAddResponse.setMessage("either you are offline or task already exist");
                return taskAddResponse;
            }else {
                Task task = new Task();
                task.setUserId(user.getEmail());
                task.setTitle(taskAddRequest.getTitle());
                task.setNote(taskAddRequest.getNote());
                task.setPriority(taskAddRequest.getPriority());
                taskRepository.save(task);
                taskAddResponse.setMessage("Task added");
                return taskAddResponse;
            }
        }
        return null;
    }

    @Override
    public String findAll(FindAllRequest findAllRequest) {
        Optional<User> userOptional = userRepository.findByEmail(findAllRequest.getEmail());
        User user = userOptional.get();
        if (userOptional.isPresent()) {
            return taskRepository.findAllByUserId(user.getEmail()).toString();
        }
        return null;
    }

    @Override
    public CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest) {
        Optional<User> userOptional = userRepository.findByEmail(completeTaskRequest.getEmail());
        User user = userOptional.get();
        if (userOptional.isPresent() && userOptional.get().isLoggedIn() == true) {
            CompleteTaskResponse completeTaskResponse = new CompleteTaskResponse();
            Optional<Task> taskOptional = taskRepository.findByTitle(completeTaskRequest.getTitle());
            Task task = taskOptional.get();
            if (task.getTitle().equals(completeTaskRequest.getTitle())) {
                task.setDone(true);
                taskRepository.save(task);
                completeTaskResponse.setTaskName(task.getTitle());
                completeTaskResponse.setMessage("task is completed");
                return completeTaskResponse;
            }
        }
        return null;
    }

    @Override
    public String dailyTaskIsComplete(CompleteTaskRequest completeTaskRequest) {
        int countTask = 0;
        apiFindAll(completeTaskRequest);
        for (Task task : apiFindAll(completeTaskRequest)) {
            if (task.isDone() == true) {
                countTask += 1;
            }
        }

        float percentage = 0;
        if (countTask >= 0 && countTask < 100) {
            percentage += Math.round((countTask / (float) apiFindAll(completeTaskRequest).size()) * 100);
            DailyCompleteResponse dailyCompleteResponse = new DailyCompleteResponse();
            dailyCompleteResponse.setMessage("Scheduled Tasks not complete");
            return "" + dailyCompleteResponse.getMessage() + ", " + percentage + "% complete";
        } else {
            DailyCompleteResponse dailyCompleteResponse = new DailyCompleteResponse();
            dailyCompleteResponse.setMessage("All Tasks completed");
            return "" + dailyCompleteResponse.getMessage() + " 100%";
        }
    }

    @Override
    public UpdateTaskResponse updateTask(CompleteTaskRequest completeTaskRequest, TaskAddRequest taskAddRequest) {
        Optional<User> userOptional = userRepository.findByEmail(completeTaskRequest.getEmail());
        User user = userOptional.get();
        if (userOptional.isPresent() && userOptional.get().isLoggedIn() == true) {
            UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
            Optional<Task> taskOptional = taskRepository.findByTitle(completeTaskRequest.getTitle());
            Task task = taskOptional.get();
            if (task.getTitle().equals(completeTaskRequest.getTitle())) {
                task.setUserId(taskAddRequest.getEmail());
                task.setTitle(taskAddRequest.getTitle());
                task.setNote(taskAddRequest.getNote());
                task.setPriority(taskAddRequest.getPriority());
                taskRepository.save(task);
                updateTaskResponse.setMessage("Task update successful");
                return updateTaskResponse;
            }
        }
        return null;
    }

    public DeleteResponse deleteTask(CompleteTaskRequest completeTaskRequest) {
        Optional<User> userOptional = userRepository.findByEmail(completeTaskRequest.getEmail());
        User user = userOptional.get();
        if (userOptional.isPresent() && userOptional.get().isLoggedIn() == true) {
            DeleteResponse deleteResponse = new DeleteResponse();
            Optional<Task> taskOptional = taskRepository.findByTitle(completeTaskRequest.getTitle());
            Task task = taskOptional.get();
            if (task.getTitle().equals(completeTaskRequest.getTitle())) {
                taskRepository.delete(task);
                String taskName = completeTaskRequest.getTitle();
                deleteResponse.setMessage(taskName + " Task deleted");
                return deleteResponse;
            }
        }
        return null;
    }

    @Override
    public List<Task> apiFindAll(CompleteTaskRequest completeTaskRequest) {
        Optional<User> userOptional = userRepository.findByEmail(completeTaskRequest.getEmail());
        User user = userOptional.get();
        if (userOptional.isPresent()) {
            return taskRepository.findAllByUserId(user.getEmail());
        }
        return null;
    }

    private void addTaskValidation(TaskAddRequest taskAddRequest) {
        if (taskAddRequest.getEmail().isEmpty() || taskAddRequest.getNote().isEmpty() || taskAddRequest.getTitle().isEmpty()) {
            throw new UserLoginException("one field is empty");
        }

        if (taskAddRequest.getEmail().equals(" ") || taskAddRequest.getNote().equals(" ") || taskAddRequest.getTitle().equals(" ")) {
            throw new EmptySpaceLoginExeption("Can not take white spaces");
        }

        Optional<Task> userOptional = taskRepository.findByTitle(taskAddRequest.getEmail());
        if (userOptional.isPresent()) {
            if (userOptional.get().getTitle().equals(taskAddRequest.getTitle())) {
                throw new UserIsPresentException("user already exist");
            }
        }
    }
}
