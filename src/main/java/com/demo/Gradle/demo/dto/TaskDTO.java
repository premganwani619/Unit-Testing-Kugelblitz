package com.demo.Gradle.demo.dto;

import com.demo.Gradle.demo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TaskDTO {
    private String taskName;
    private String description;
    private Date dueDate;
    private Status taskStatus;
    private List<CategoryDTO> category;
     public TaskDTO(){
        this.taskStatus=Status.PENDING;
    }
}
