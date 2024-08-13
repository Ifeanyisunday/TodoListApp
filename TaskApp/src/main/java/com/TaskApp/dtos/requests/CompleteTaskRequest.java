package com.TaskApp.dtos.requests;

import lombok.Data;

@Data
public class CompleteTaskRequest {
    private String title;
    private String email;
}
