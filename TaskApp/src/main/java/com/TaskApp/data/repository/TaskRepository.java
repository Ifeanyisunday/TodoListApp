package com.TaskApp.data.repository;


import com.TaskApp.data.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findByUserId(String userId);
    List<Task> findAllByUserId(String userId);
    Optional<Task> findByTitle(String title);
}
