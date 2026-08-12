package lk.ijse.cmjd113.FoodOrder.dao;

import lk.ijse.cmjd113.FoodOrder.entity.FoodItemEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.FoodItemStatus;

import java.util.List;
import java.util.Optional;

public interface FoodItemDao {
    FoodItemEntity save(FoodItemEntity foodItem);
    Optional<FoodItemEntity> findById(String foodItemId);
    List<FoodItemEntity> findAll();
    List<FoodItemEntity> findByCategoryId(String categoryId);
    List<FoodItemEntity> findByStatus(FoodItemStatus status);
    List<FoodItemEntity> searchByName(String name);
    void deleteById(String foodItemId);
}
