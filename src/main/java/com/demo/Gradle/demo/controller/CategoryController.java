package com.demo.Gradle.demo.controller;

import com.demo.Gradle.demo.dto.CategoryDTO;
import com.demo.Gradle.demo.dto.responsedto.CategoryResponseDTO;
import com.demo.Gradle.demo.entity.Category;
import com.demo.Gradle.demo.service.CategoryService;
import com.demo.Gradle.demo.transformers.CategoryTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryResponseDTO createCategory(@RequestBody CategoryDTO newCategory) {
        return categoryService.createCategory(newCategory);
    }

    @PostMapping("/addAll")
    public List<CategoryResponseDTO> addAllCategories(@RequestBody List<CategoryDTO> categories) {
        return categoryService.addAllCategories(categories);
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCategoryById(@PathVariable int id) {
         categoryService.deleteCategoryById(id);
         return "Category Deleted Successfully";
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategoryById(@PathVariable int id, @RequestBody CategoryDTO updatedCategory) {
        return categoryService.updateCategoryById(id, updatedCategory);
    }
}
