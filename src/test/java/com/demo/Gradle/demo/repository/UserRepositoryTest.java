package com.demo.Gradle.demo.repository;

import com.demo.Gradle.demo.entity.Category;
import com.demo.Gradle.demo.entity.Task;
import com.demo.Gradle.demo.entity.User;
import com.demo.Gradle.demo.enums.Status;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .categoryName("random category")
                .description("random category description")
                .build();

        Task task = Task.builder()
                .taskName("random task name")
                .description("random task description")
                .taskStatus(Status.PENDING)
                .category(Collections.singletonList(category))
                .dueDate(new Date())
                .build();

        User user = User.builder()
                .userName("prem")
                .email("premsoni0474@gmail.com")
                .password("password")
                .task(Collections.singletonList(task))
                .build();
        userRepository.save(user);
    }

    @Test
    void save_UserSavedSuccessfully() {
        // Test saving a user
        Category category = Category.builder()
                .categoryName("random category")
                .description("random category description")
                .build();

        Task task = Task.builder()
                .taskName("random task name")
                .description("random task description")
                .taskStatus(Status.PENDING)
                .category(Collections.singletonList(category))
                .dueDate(new Date())
                .build();

        User user = User.builder()
                .userName("prem")
                .email("premsoni0474@gmail.com")
                .password("password")
                .task(Collections.singletonList(task))
                .build();
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getUserId());
        assertEquals("prem", savedUser.getUserName());
    }

    @Test
    void findById_UserFound() {
        // Test finding a user by ID
        User user = userRepository.findAll().get(0);
        Optional<User> optionalUser = userRepository.findById(Math.toIntExact(user.getUserId()));

        assertTrue(optionalUser.isPresent());
        assertEquals(user.getUserName(), optionalUser.get().getUserName());
    }

    @Test
    void findAll_MultipleUsersFound() {
        // Test finding all users
        List<User> userList = userRepository.findAll();

        assertFalse(userList.isEmpty());
        assertEquals(1, userList.size());
    }

    @Test
    void update_UserUpdatedSuccessfully() {
        User user = userRepository.findAll().get(0);
        user.setUserName("updatedName");
        int id = Math.toIntExact(user.getUserId());
        try{
            userRepository.updateUser(id,user.getUserName(),user.getEmail(),user.getPassword());
        }
        catch (Exception e){
            fail("Updating user failed: " + e.getMessage());
        }
        assertEquals("updatedName", user.getUserName());
    }

    @Test
    void delete_UserDeletedSuccessfully() {
        // Test deleting a user
        User user = userRepository.findAll().get(0);
        userRepository.delete(user);

        assertFalse(userRepository.existsById(Math.toIntExact(user.getUserId())));
    }


}
