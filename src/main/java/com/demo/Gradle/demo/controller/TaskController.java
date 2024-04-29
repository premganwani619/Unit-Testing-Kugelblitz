package com.demo.Gradle.demo.controller;

import com.demo.Gradle.demo.dto.TaskDTO;
import com.demo.Gradle.demo.dto.responsedto.TaskResponseDTO;
import com.demo.Gradle.demo.entity.User;
import com.demo.Gradle.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskResponseDTO addTask(@RequestBody TaskDTO task) {
        return taskService.addTask(task);
    }

    @PostMapping("/addTasks")
    public List<TaskResponseDTO> addTasks(@RequestBody List<TaskDTO> tasks) {
        return taskService.addTasks(tasks);
    }


    @DeleteMapping("/{id}")
    public String deleteTaskById(@PathVariable int id) {
        return taskService.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTaskById(@PathVariable int id, @RequestBody TaskResponseDTO updatedTask) {
        return taskService.updateTaskById(id, updatedTask);
    }
}
