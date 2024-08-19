package com.TaskApp.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Task {

    @Id
    private String taskId;
    private String userId;
    private String title;
    private String note;
    private String priority;
    private boolean isDone;

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}
