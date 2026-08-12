package lk.ijse.cmjd113.FoodOrder.dao;

import lk.ijse.cmjd113.FoodOrder.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    CategoryEntity save(CategoryEntity category);
    Optional<CategoryEntity> findById(String categoryId);
    List<CategoryEntity> findAll();
    void deleteById(String categoryId);
    boolean existsByName(String name);
}
