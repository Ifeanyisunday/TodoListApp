package com.TaskApp.data.repository;

import com.TaskApp.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findUserByUserId(String userId);
    Optional<User> findByUserName(String userName);
}
