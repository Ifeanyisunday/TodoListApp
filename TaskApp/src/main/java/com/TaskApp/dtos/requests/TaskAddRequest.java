package com.TaskApp.dtos.requests;

import lombok.Data;

@Data
public class TaskAddRequest {
    private String title;
    private String note;
    private String priority;
    private String email;
}