package com.demo.Gradle.demo.dto.responsedto;

import com.demo.Gradle.demo.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private long id;
    private String userName;
    private String email;
    private String password;
}
