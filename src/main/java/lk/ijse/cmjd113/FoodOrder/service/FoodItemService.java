package lk.ijse.cmjd113.FoodOrder.service;

import lk.ijse.cmjd113.FoodOrder.dto.request.FoodItemRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.FoodItemResponseDto;

import java.util.List;

public interface FoodItemService {
    FoodItemResponseDto createFoodItem(FoodItemRequestDto request);
    FoodItemResponseDto updateFoodItem(String foodItemId, FoodItemRequestDto request);
    FoodItemResponseDto getFoodItemById(String foodItemId);
    List<FoodItemResponseDto> getAllFoodItems();
    List<FoodItemResponseDto> getFoodItemsByCategory(String categoryId);
    List<FoodItemResponseDto> searchFoodItems(String name);
    void deleteFoodItem(String foodItemId);
}
