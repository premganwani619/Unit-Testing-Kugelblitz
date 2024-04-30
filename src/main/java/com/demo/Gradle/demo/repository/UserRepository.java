package com.demo.Gradle.demo.repository;

import com.demo.Gradle.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userName = :userName, u.email = :email, u.password = :password WHERE u.userId = :id")
    void updateUser(@Param("id") Integer id, @Param("userName") String userName, @Param("email") String email, @Param("password") String password);


    @Override
    <S extends User> S save(S entity);

    default <S extends User> S saveUser(S entity) throws Exception {
        // Check if any required fields are null
        if (entity.getUserName() == null || entity.getEmail() == null || entity.getPassword() == null) {
            throw new Exception("User must not have null values for username, email, and password.");
        }
        return save(entity);
    }

    Optional<User> findByEmail(String email);
}
