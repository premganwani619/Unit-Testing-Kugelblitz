package com.demo.Gradle.demo.entity;

import com.demo.Gradle.demo.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int taskId;

    private String taskName;
    private String description;
    private Date dueDate;
    private Status taskStatus;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> category;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

}


