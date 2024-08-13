package com.TaskApp.dtos.responses;

import lombok.Data;

@Data
public class CompleteTaskResponse {
    private String taskName;
    private String message;
}
