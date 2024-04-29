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
    void addUser() throws Exception, InvalidUserException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO input = UserDTO.builder()
                .userName("prem")
                .email("premsoni0474@gmail.com")
                .password("password")
                .build();
        String requestBody = objectMapper.writeValueAsString(input);
        Mockito.when(userService.addUser(input)).thenReturn(userResponseDTO);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk());
    }

    @Test
    void getUserById() throws Exception {
        Mockito.when(userService.getUserById(1)).thenReturn(userResponseDTO);
        mockMvc.perform(get("/users/1")).andExpect(status().isOk());
    }

    @Test
    void getAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(userResponseDTOS);
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    void updateUserById() throws Exception {
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
    void deleteUserById() throws Exception {
        Mockito.when(userService.deleteUserById(1)).thenReturn("user deleted successfully");
        mockMvc.perform(delete("/users/1")).andExpect(status().isOk());
    }
//
//    @Test
//    @Disabled
//    void addUser_InvalidInput() throws InvalidUserException, Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserDTO input = UserDTO.builder()
//                .userName("prem")
//                .build();
//        String requestBody = objectMapper.writeValueAsString(input);
//
//        Mockito.when(userService.addUser(input)).thenThrow(new InvalidUserException("Invalid user details"));
//
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid user details"));
//    }
//
//    @Test
//    @Disabled
//    void getUserById_UserNotFound() throws Exception {
//        Mockito.when(userService.getUserById(999)).thenReturn(null);
//        mockMvc.perform(get("/users/999"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Disabled
//    void updateUserById_UserNotFound() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserResponseDTO input = UserResponseDTO.builder()
//                .userName("updated username")
//                .email("updateduser@gmail.com")
//                .password("updated-password")
//                .build();
//        String requestBody = objectMapper.writeValueAsString(input);
//
//        Mockito.when(userService.updateUserById(999, input)).thenReturn(null);
//
//        mockMvc.perform(put("/users/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Disabled
//    void deleteUserById_UserNotFound() throws Exception {
//        Mockito.when(userService.deleteUserById(999)).thenReturn(null);
//        mockMvc.perform(delete("/users/999"))
//                .andExpect(status().isNotFound());
//    }

}