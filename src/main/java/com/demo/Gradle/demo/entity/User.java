package com.demo.Gradle.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @NotNull
    @Valid
    private String userName;

    @NotNull
    @Valid
    private String email;

    @NotNull
    @Valid
    private String password;

    @NotNull
    @Valid
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> task;

}

