package com.demo.Gradle.demo.repository;

import com.demo.Gradle.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userName = :userName, u.email = :email, u.password = :password WHERE u.userId = :id")
    void updateUser(@Param("id") Integer id, @Param("userName") String userName, @Param("email") String email, @Param("password") String password);

}
