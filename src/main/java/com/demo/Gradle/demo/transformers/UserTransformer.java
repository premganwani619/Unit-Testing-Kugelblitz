package com.demo.Gradle.demo.transformers;

import com.demo.Gradle.demo.dto.UserDTO;
import com.demo.Gradle.demo.dto.responsedto.UserResponseDTO;
import com.demo.Gradle.demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserTransformer {

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }


    public UserResponseDTO toResponseDTO(int id,UserDTO userDTO){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(id);
        userResponseDTO.setUserName(userDTO.getUserName());
        userResponseDTO.setEmail(userDTO.getEmail());
        userResponseDTO.setPassword(userDTO.getPassword());
        return userResponseDTO;
    }
    public UserResponseDTO toResponseDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getUserId());
        userResponseDTO.setUserName(user.getUserName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPassword(user.getPassword());
        return userResponseDTO;
    }

//    public User toEntity(UserDTO dto) {
//        User user = new User();
//        user.setUserName(dto.getUserName());
//        user.setEmail(dto.getEmail());
//        user.setPassword(dto.getPassword());
//        user.setTasks(dto.getTasks().stream().map(TaskTransformer::toEntity).collect(Collectors.toList()));
//        return user;
//    }
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        // Assuming `savedUser` is the newly created User entity
        // and `dto.getTasks()` returns a list of TaskDTO objects
        return user;
    }

    public User toEntity(UserResponseDTO dto) {
        User user = new User();
        user.setUserId(dto.getId());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public List<User> toEntityList(List<UserResponseDTO> dtos) {
        List<User> users = new ArrayList<>();
        for (UserResponseDTO dto : dtos) {
            users.add(toEntity(dto));
        }
        return users;
    }
}
