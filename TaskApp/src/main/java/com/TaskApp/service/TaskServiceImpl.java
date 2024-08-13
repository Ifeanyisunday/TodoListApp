package com.TaskApp.service;

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
        Optional<User> userOptional = userRepository.findByEmail(taskAddRequest.getEmail());
        TaskAddResponse taskAddResponse = new TaskAddResponse();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isLoggedIn()) {
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
        if (countTask >= 0) {
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
                deleteResponse.setMessage(taskName + "Task deleted");
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
}
