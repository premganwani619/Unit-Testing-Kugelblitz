package com.demo.Gradle.demo.service;

import com.demo.Gradle.demo.dto.CategoryDTO;
import com.demo.Gradle.demo.dto.TaskDTO;
import com.demo.Gradle.demo.dto.UserDTO;
import com.demo.Gradle.demo.dto.responsedto.UserResponseDTO;
import com.demo.Gradle.demo.entity.User;
import com.demo.Gradle.demo.enums.Status;
import com.demo.Gradle.demo.repository.UserRepository;
import com.demo.Gradle.demo.transformers.UserTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    private final UserService userService;

    private final UserTransformer userTransformer;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    UserServiceTest(UserService userService, UserTransformer userTransformer) {
        this.userService = userService;
        this.userTransformer = userTransformer;
    }


    @BeforeEach
    void setUp() {
        User user = User.builder()
                .userId(1L)
                .userName("prem")
                .email("premsoni0474@gmail.com")
                .password("password")
                .build();
        List<User> users = Collections.singletonList(user);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
    }

    @Test
    void getAllUsers() {
        String userName = "prem";
        List<User> users = userTransformer.toEntityList(userService.getAllUsers());
        assertEquals(userName, users.get(0).getUserName());
    }

    @Test
    void getUserById() {
        String userName = "prem";
        UserResponseDTO userResponseDTO = userService.getUserById(1);
        assertEquals(userName, userResponseDTO.getUserName());
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(1);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1);

    }

    @Test
    void addUser() {
        CategoryDTO category = CategoryDTO.builder()
                .categoryName("random category")
                .description("random category description")
                .build();

        TaskDTO task = TaskDTO.builder()
                .taskName("random task name")
                .description("random task description")
                .taskStatus(Status.PENDING)
                .category(Collections.singletonList(category))
                .dueDate(new Date())
                .build();

        UserDTO user = UserDTO.builder()
                .userName("prem")
                .email("premsoni0474@gmail.com")
                .password("password")
                .tasks(Collections.singletonList(task))
                .build();
        UserResponseDTO addedUser = userService.addUser(user);
        assertEquals("prem", addedUser.getUserName());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }


    @Test
    void updateUserById() {
        UserResponseDTO updatedUser = UserResponseDTO.builder()
                .userName("updatedUser")
                .email("updateduser@example.com")
                .password("updatedpassword")
                .build();
        UserResponseDTO updatedUserResponse = userService.updateUserById(1, updatedUser);
        assertEquals("updatedUser", updatedUserResponse.getUserName());
        assertEquals("updateduser@example.com", updatedUserResponse.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).updateUser(1, updatedUser.getUserName(), updatedUser.getEmail(), updatedUser.getPassword());
    }
}