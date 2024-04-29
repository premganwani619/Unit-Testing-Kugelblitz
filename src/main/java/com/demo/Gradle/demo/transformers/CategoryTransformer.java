package com.demo.Gradle.demo.transformers;

import com.demo.Gradle.demo.dto.CategoryDTO;
import com.demo.Gradle.demo.dto.responsedto.CategoryResponseDTO;
import com.demo.Gradle.demo.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryTransformer {
    public static List<CategoryDTO> toDTOList(List<Category> tasks) {
        return tasks.stream()
                .map(CategoryTransformer::toDTO)
                .collect(Collectors.toList());
    }
    public static List<CategoryResponseDTO> toResponseDTOList(List<Category> tasks) {
        return tasks.stream()
                .map(CategoryTransformer::toResponseDTO)
                .collect(Collectors.toList());
    }

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }
    public static CategoryResponseDTO toResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getCategoryID());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public static CategoryResponseDTO toResponseDTO(int id,CategoryDTO updatedCategory) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(id);
        categoryResponseDTO.setCategoryName(updatedCategory.getCategoryName());
        categoryResponseDTO.setDescription(updatedCategory.getDescription());
        return categoryResponseDTO;
    }
}
