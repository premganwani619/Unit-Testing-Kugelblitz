package com.demo.Gradle.demo.service;

import com.demo.Gradle.demo.dto.CategoryDTO;
import com.demo.Gradle.demo.dto.responsedto.CategoryResponseDTO;
import com.demo.Gradle.demo.entity.Category;
import com.demo.Gradle.demo.repository.CategoryRepository;
import com.demo.Gradle.demo.transformers.CategoryTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> getAllCategories() {
        return CategoryTransformer.toResponseDTOList(categoryRepository.findAll());
    }

    public CategoryResponseDTO getCategoryById(int categoryId) {
        return CategoryTransformer.toResponseDTO(categoryRepository.findById(categoryId).get());
    }

    public CategoryResponseDTO createCategory(CategoryDTO newCategory) {
        Category category = CategoryTransformer.toEntity(newCategory);
        Category savedCategory = categoryRepository.save(category);
        return CategoryTransformer.toResponseDTO(savedCategory);
    }

    public List<CategoryResponseDTO> addAllCategories(List<CategoryDTO> newCategoryDTOs) {
        List<Category> newCategories = newCategoryDTOs.stream()
                .map(CategoryTransformer::toEntity)
                .collect(Collectors.toList());

        List<Category> savedCategories = categoryRepository.saveAll(newCategories);

        return savedCategories.stream()
                .map(CategoryTransformer::toResponseDTO)
                .collect(Collectors.toList());
    }


    public void deleteCategoryById(int categoryId) {

        categoryRepository.deleteById(categoryId);
    }

    public CategoryResponseDTO updateCategoryById(int categoryId, CategoryDTO updatedCategory) {
        categoryRepository.updateCategoryById(categoryId,updatedCategory.getCategoryName(),updatedCategory.getDescription());
        return CategoryTransformer.toResponseDTO(categoryId,updatedCategory);
    }
}
