package com.demo.Gradle.demo.transformers;

import com.demo.Gradle.demo.dto.TaskDTO;
import com.demo.Gradle.demo.dto.responsedto.TaskResponseDTO;
import com.demo.Gradle.demo.entity.Task;
import com.demo.Gradle.demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskTransformer {
    public static List<TaskDTO> toDTOList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskTransformer::toDTO) // Assuming toDTO(Task task) is the method you defined earlier
                .collect(Collectors.toList());
    }

    public static List<Task> toEntityList(List<TaskDTO> dtos) {
        return dtos.stream()
                .map(TaskTransformer::toEntity) // Assuming toEntity(User savedUser, TaskDTO dto) is the method you defined earlier
                .collect(Collectors.toList());
    }
    public static TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskName(task.getTaskName());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setTaskStatus(task.getTaskStatus());
        dto.setCategory(task.getCategory().stream().map(CategoryTransformer::toDTO).collect(Collectors.toList()));
        return dto;
    }

    public static TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setTaskStatus(task.getTaskStatus());
        return dto;
    }

    public static Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTaskName(dto.getTaskName());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setTaskStatus(dto.getTaskStatus());
        task.setCategory(dto.getCategory().stream().map(CategoryTransformer::toEntity).collect(Collectors.toList()));
        return task;
    }
}
