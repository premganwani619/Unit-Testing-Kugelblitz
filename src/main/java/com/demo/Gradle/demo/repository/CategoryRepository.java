package com.demo.Gradle.demo.repository;

import com.demo.Gradle.demo.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {



    @Modifying
    @Transactional
    @Query(value = "UPDATE Category category SET category.category_name = :name, category.description = :desc WHERE category.categoryid = :id",nativeQuery = true)
    void updateCategoryById(@Param("id") Integer id, @Param("name") String name, @Param("desc") String description);

}
