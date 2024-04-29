package com.demo.Gradle.demo.dto.responsedto;

import com.demo.Gradle.demo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TaskResponseDTO {
    private int id;
    private String taskName;
    private String description;
    private Date dueDate;
    private Status taskStatus;

    public TaskResponseDTO() {
        this.taskStatus = Status.PENDING;
    }

}
