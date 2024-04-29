package com.demo.Gradle.demo.controller;

import com.demo.Gradle.demo.dto.UserDTO;
import com.demo.Gradle.demo.dto.responsedto.UserResponseDTO;
import com.demo.Gradle.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseDTO addUser(@RequestBody UserDTO newUser) {
        return userService.addUser(newUser);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUserById(@PathVariable int id, @RequestBody UserResponseDTO updatedUser) {
        return userService.updateUserById(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable int id) {
        return userService.deleteUserById(id);
    }


}
