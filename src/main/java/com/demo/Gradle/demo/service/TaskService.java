package com.demo.Gradle.demo.service;

import com.demo.Gradle.demo.dto.TaskDTO;
import com.demo.Gradle.demo.dto.responsedto.TaskResponseDTO;
import com.demo.Gradle.demo.entity.Category;
import com.demo.Gradle.demo.entity.Task;
import com.demo.Gradle.demo.entity.User;
import com.demo.Gradle.demo.repository.TaskRepository;
import com.demo.Gradle.demo.transformers.CategoryTransformer;
import com.demo.Gradle.demo.transformers.TaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskTransformer::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskById(int id) {
        Task task = taskRepository.findById(id).get();

        return TaskTransformer.toResponseDTO((task));
    }

    public TaskResponseDTO addTask(TaskDTO taskDTO) {
        Task task = TaskTransformer.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        return TaskTransformer.toResponseDTO(savedTask);
    }

//    public TaskResponseDTO addTask(TaskDTO taskDTO) {
//        Task task = new Task();
//        task.setTaskName("random task name");
//        task.setDescription("random task description");
//        task.setDueDate(new Date());
//        Task savedTask = taskRepository.save(task);
//        List<Category> category = task.getCategory();
//        categoryService.addAllCategories(CategoryTransformer.toDTOList(task.getCategory()));
//        return TaskTransformer.toResponseDTO(savedTask);
//    }

    public List<TaskResponseDTO> addTasks(List<TaskDTO> tasks) {
        List<TaskResponseDTO> savedTasks = new ArrayList<>();
        for (TaskDTO task : tasks) {
            savedTasks.add(addTask(task));
        }
        return savedTasks;
    }

    public String deleteTaskById(int id) {
//        taskRepository.setUserIdNull(id);
        taskRepository.deleteById(id);
        return "Task Deleted Successfully";
    }

    public TaskResponseDTO updateTaskById(int taskId, TaskResponseDTO updatedTask) {
        Optional<TaskResponseDTO> optionalTask = Optional.of(TaskTransformer.toResponseDTO(taskRepository.findById(taskId).get()));
        TaskResponseDTO task = optionalTask.get();
        task.setTaskName(updatedTask.getTaskName());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        taskRepository.updateTask(taskId,task.getTaskName(),task.getDescription(),task.getDueDate());
        return task;
    }


}

