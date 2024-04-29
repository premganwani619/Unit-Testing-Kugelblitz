package com.demo.Gradle.demo.repository;

import com.demo.Gradle.demo.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "update task set user_user_id=null where task_id=:id", nativeQuery = true)
    @Modifying
    @Transactional
    public void setUserIdNull(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.taskName = :taskName, t.description = :description, t.dueDate = :dueDate WHERE t.taskId = :id")
    void updateTask(@Param("id") Integer id, @Param("taskName") String taskName, @Param("description") String description, @Param("dueDate") Date dueDate);

/*
    private String taskName;
    private String description;
    private Date dueDate;
*/
}
