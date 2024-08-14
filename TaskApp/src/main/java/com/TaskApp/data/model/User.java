package com.TaskApp.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class User {

    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    private boolean isLoggedIn = true;
}
