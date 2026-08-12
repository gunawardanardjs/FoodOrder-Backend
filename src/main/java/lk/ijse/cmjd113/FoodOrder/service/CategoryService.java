package lk.ijse.cmjd113.FoodOrder.service;

import lk.ijse.cmjd113.FoodOrder.dto.request.CategoryRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto request);
    CategoryResponseDto updateCategory(String categoryId, CategoryRequestDto request);
    CategoryResponseDto getCategoryById(String categoryId);
    List<CategoryResponseDto> getAllCategories();
    void deleteCategory(String categoryId);
}
