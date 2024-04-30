package com.demo.Gradle.demo.controller;

import com.demo.Gradle.demo.dto.UserDTO;
import com.demo.Gradle.demo.dto.responsedto.UserResponseDTO;
import com.demo.Gradle.demo.exceptions.InvalidUserException;
import com.demo.Gradle.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());


    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserDTO newUser) {
        try {
            UserResponseDTO createdUser = userService.addUser(newUser);
            logger.info("User added successfully: " + createdUser.getUserName());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (InvalidUserException ex) {
            logger.warning("Failed to add user: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        try {
            List<UserResponseDTO> users = userService.getAllUsers();
            logger.info("Retrieved all users");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (InvalidUserException ex) {
            logger.severe("Failed to retrieve users: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
        try {
            UserResponseDTO user = userService.getUserById(id);
            logger.info("Retrieved user with id: " + id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (InvalidUserException ex) {
            logger.warning("Failed to retrieve user with id " + id + ": " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable int id, @RequestBody UserResponseDTO updatedUser) {
        try {
            UserResponseDTO updatedUserData = userService.updateUserById(id, updatedUser);
            logger.info("User updated successfully with id: " + id);
            return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
        } catch (InvalidUserException ex) {
            logger.warning("Failed to update user with id " + id + ": " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        try {
            String message = userService.deleteUserById(id);
            logger.info("User deleted successfully with id: " + id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InvalidUserException ex) {
            logger.warning("Failed to delete user with id " + id + ": " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
