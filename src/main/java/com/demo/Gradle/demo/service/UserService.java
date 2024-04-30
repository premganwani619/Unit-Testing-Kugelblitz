package com.demo.Gradle.demo.service;

import com.demo.Gradle.demo.dto.UserDTO;
import com.demo.Gradle.demo.dto.responsedto.UserResponseDTO;
import com.demo.Gradle.demo.entity.Task;
import com.demo.Gradle.demo.entity.User;
import com.demo.Gradle.demo.exceptions.InvalidUserException;
import com.demo.Gradle.demo.repository.UserRepository;
import com.demo.Gradle.demo.transformers.TaskTransformer;
import com.demo.Gradle.demo.transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTransformer userTransformer;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(UserRepository userRepository, UserTransformer userTransformer) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
    }

    @Transactional
    public List<UserResponseDTO> getAllUsers() throws InvalidUserException {
        try {
            List<User> users = userRepository.findAll();
            logger.info("Retrieved all users from the database");
            return users.stream()
                    .map(userTransformer::toResponseDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            logger.severe("Failed to retrieve users from the database: " + ex.getMessage());
            throw new InvalidUserException("Failed to fetch users");
        }
    }

    @Transactional
    public UserResponseDTO getUserById(int id) throws InvalidUserException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            logger.info("Retrieved user with id: " + id + " from the database");
            return userTransformer.toResponseDTO(user);
        } catch (RuntimeException ex) {
            logger.warning("Failed to retrieve user with id " + id + " from the database: " + ex.getMessage());
            throw new InvalidUserException("Failed to fetch user with id: " + id);
        }
    }

    @Transactional
    public String deleteUserById(int id) throws InvalidUserException {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userRepository.deleteById(id);
                logger.info("User deleted successfully with id: " + id);
                return "User Deleted Successfully";
            } else {
                throw new InvalidUserException("User not found with id: " + id);
            }
        } catch (Exception ex) {
            logger.warning("Failed to delete user with id " + id + ": " + ex.getMessage());
            throw new InvalidUserException("Failed to delete user with id: " + id);
        }
    }

    @Transactional
    public UserResponseDTO addUser(UserDTO newUserDTO) throws InvalidUserException {
        try {
            Optional<User> existingUserOptional = userRepository.findByEmail(newUserDTO.getEmail());

            if (existingUserOptional.isPresent()) {
                throw new InvalidUserException("User with email " + newUserDTO.getEmail() + " already exists");
            }

            User user = new User();
            user.setUserName(newUserDTO.getUserName());
            user.setEmail(newUserDTO.getEmail());
            user.setPassword(newUserDTO.getPassword());

            List<Task> tasks = newUserDTO.getTasks().stream()
                    .map(taskDto -> {
                        Task task = TaskTransformer.toEntity(taskDto);
                        task.setUser(user);
                        return task;
                    })
                    .collect(Collectors.toList());

            user.setTask(tasks);
            User savedUser = userRepository.save(user);

            logger.info("User added successfully with email: " + newUserDTO.getEmail());
            return userTransformer.toResponseDTO(savedUser);
        } catch (DataAccessException ex) {
            logger.severe("Failed to add user: " + ex.getMessage());
            throw new InvalidUserException("Failed to add user");
        } catch (NullPointerException ex) {
            logger.warning("Failed to add user: User not found");
            throw new InvalidUserException("Failed to add user: User not found");
        }
    }

    @Transactional
    public UserResponseDTO updateUserById(int id, UserResponseDTO userUpdatedDTO) throws InvalidUserException {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                throw new InvalidUserException("User not found with id: " + id);
            }
            User existingUser = optionalUser.get();
            existingUser.setUserName(userUpdatedDTO.getUserName());
            existingUser.setEmail(userUpdatedDTO.getEmail());
            existingUser.setPassword(userUpdatedDTO.getPassword());
            userRepository.updateUser(id, existingUser.getUserName(), existingUser.getEmail(), existingUser.getPassword());
            logger.info("User updated successfully with id: " + id);
            return userTransformer.toResponseDTO(existingUser);
        } catch (Exception ex) {
            logger.warning("Failed to update user with id " + id + ": " + ex.getMessage());
            throw new InvalidUserException("Failed to update user with id: " + id);
        }
    }
}
