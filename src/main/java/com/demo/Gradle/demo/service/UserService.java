package com.demo.Gradle.demo.service;

import com.demo.Gradle.demo.dto.UserDTO;
import com.demo.Gradle.demo.dto.responsedto.UserResponseDTO;
import com.demo.Gradle.demo.entity.Task;
import com.demo.Gradle.demo.entity.User;
import com.demo.Gradle.demo.repository.UserRepository;
import com.demo.Gradle.demo.transformers.TaskTransformer;
import com.demo.Gradle.demo.transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;



    private final UserTransformer userTransformer;

    @Autowired
    public UserService(UserRepository userRepository, UserTransformer userTransformer) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userTransformer::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(int id) {
        User user = userRepository.findById(id).get();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getUserId());
        userResponseDTO.setUserName(user.getUserName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPassword(user.getPassword());
        return userResponseDTO;

    }

    public String deleteUserById(int id) {
        userRepository.deleteById(id);
        return "User Deleted Successfully";

    }

//    public UserDTO addUser(UserDTO newUserDTO) {
//        User user = new User();
//        user.setUserName(newUserDTO.getUserName());
//        user.setEmail(newUserDTO.getEmail());
//        user.setPassword(newUserDTO.getPassword());
//        user.setTasks(newUserDTO.getTasks().stream().map(taskDto -> TaskTransformer.toEntity(user, taskDto)).collect(Collectors.toList()));
////        User newUser = userTransformer.toEntity(newUserDTO);
//        User savedUser = userRepository.save(user);
//        return userTransformer.toDTO(savedUser);
//    }

//    public UserResponseDTO addUser(UserDTO newUserDTO) {
//        User user = new User();
//        user.setUserName(newUserDTO.getUserName());
//        user.setEmail(newUserDTO.getEmail());
//        user.setPassword(newUserDTO.getPassword());
//        User savedUser = userRepository.save(user);
//
//        List<Task> tasks = newUserDTO.getTasks().stream()
//                .map(taskDto -> {
//                    Task task = TaskTransformer.toEntity(taskDto);
//                    task.setUser(savedUser);
//                    return task;
//                })
//                .collect(Collectors.toList());
//
//        List<Task> savedTasks = taskRepository.saveAll(tasks);
//
//        return userTransformer.toResponseDTO(savedUser);
//    }
    public UserResponseDTO addUser(UserDTO newUserDTO) {
        User user = new User();
        user.setUserName(newUserDTO.getUserName());
        user.setEmail(newUserDTO.getEmail());
        user.setPassword(newUserDTO.getPassword());

        // Set tasks for the user
        List<Task> tasks = newUserDTO.getTasks().stream()
                .map(taskDto -> {
                    Task task = TaskTransformer.toEntity(taskDto);
                    task.setUser(user); // Set user for the task
                    return task;
                })
                .collect(Collectors.toList());

        user.setTask(tasks); // Set tasks for the user entity
        User savedUser = userRepository.save(user);

        return userTransformer.toResponseDTO(savedUser);
    }



    public UserResponseDTO updateUserById(int id, UserResponseDTO userUpdatedDTO) {
        Optional<UserDTO> optionalUser = Optional.of(userTransformer.toDTO(userRepository.findById(id).get()));
        UserDTO existingUser = optionalUser.get();
        existingUser.setUserName(userUpdatedDTO.getUserName());
        existingUser.setEmail(userUpdatedDTO.getEmail());
        existingUser.setPassword(userUpdatedDTO.getPassword());
        userRepository.updateUser(id, existingUser.getUserName(),existingUser.getEmail(),existingUser.getPassword());
        return userTransformer.toResponseDTO(id,existingUser);
    }


}
