package com.demo.Gradle.demo.controller;

import com.demo.Gradle.demo.dto.UserDTO;
import com.demo.Gradle.demo.dto.responsedto.UserResponseDTO;
import com.demo.Gradle.demo.exceptions.InvalidUserException;
import com.demo.Gradle.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserResponseDTO userResponseDTO;
    private UserResponseDTO updatedUserResponseDTO;
    private List<UserResponseDTO> userResponseDTOS;

    @BeforeEach
    void setUp() {
        userResponseDTO = UserResponseDTO.builder()
                .id(1)
                .userName("prem")
                .email("premsoni0474@gmail.com")
                .password("password")
                .build();
        userResponseDTOS = Collections.singletonList(userResponseDTO);
        updatedUserResponseDTO = UserResponseDTO.builder()
                .userName("updated username")
                .email("updateduser@gmail.com")
                .password("updated-password")
                .build();
    }

    @Test
    void addUser_GoodScenario() throws Exception, InvalidUserException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO input = UserDTO.builder()
                .userName("prem")
                .email("premsoni0474@gmail.com")
                .password("password")
                .build();
        String requestBody = objectMapper.writeValueAsString(input);
        Mockito.when(userService.addUser(input)).thenReturn(userResponseDTO);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated()); // Adjusted to expect HTTP status 201
    }
    @Test
    void addUser_BadScenario_InvalidUser() throws Exception,InvalidUserException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO input = UserDTO.builder()
                .userName(null)
                .email("premsoni0474@gmail.com")
                .password("password")
                .build();
        String requestBody = objectMapper.writeValueAsString(input);

        Mockito.when(userService.addUser(input)).thenThrow(new InvalidUserException("Invalid user"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getUserById_GoodScenario() throws Exception, InvalidUserException {
        Mockito.when(userService.getUserById(1)).thenReturn(userResponseDTO);
        mockMvc.perform(get("/users/1")).andExpect(status().isOk());
    }

    @Test
    void getUserById_BadScenario_UserNotFound() throws Exception, InvalidUserException {
        Mockito.when(userService.getUserById(4)).thenThrow(new InvalidUserException("User not found"));
        mockMvc.perform(get("/users/4")).andExpect(status().isBadRequest());
    }

    @Test
    void getAllUsers_GoodScenario() throws Exception, InvalidUserException {
        Mockito.when(userService.getAllUsers()).thenReturn(userResponseDTOS);
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    void updateUserById_GoodScenario() throws Exception, InvalidUserException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserResponseDTO input = UserResponseDTO.builder()
                .userName("updated username")
                .email("updateduser@gmail.com")
                .password("updated-password")
                .build();
        String requestBody = objectMapper.writeValueAsString(input);
        Mockito.when(userService.updateUserById(1, input)).thenReturn(updatedUserResponseDTO);
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk());
    }

    @Test
    void updateUserById_BadScenario_UserNotFound() throws Exception, InvalidUserException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserResponseDTO input = UserResponseDTO.builder()
                .userName("updated username")
                .email("updateduser@gmail.com")
                .password("updated-password")
                .build();
        String requestBody = objectMapper.writeValueAsString(input);
        Mockito.when(userService.updateUserById(4, input)).thenThrow(new InvalidUserException("User not found"));
        mockMvc.perform(put("/users/4").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isBadRequest());
    }

    @Test
    void deleteUserById_GoodScenario() throws Exception, InvalidUserException {
        Mockito.when(userService.deleteUserById(1)).thenReturn("user deleted successfully");
        mockMvc.perform(delete("/users/1")).andExpect(status().isOk());
    }

    @Test
    void deleteUserById_BadScenario_UserNotFound() throws Exception, InvalidUserException {
        Mockito.when(userService.deleteUserById(100)).thenThrow(new InvalidUserException("User not found"));
        mockMvc.perform(delete("/users/100")).andExpect(status().isBadRequest());
    }

}
