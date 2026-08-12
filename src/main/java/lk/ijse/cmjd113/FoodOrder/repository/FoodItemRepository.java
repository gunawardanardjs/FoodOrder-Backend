package lk.ijse.cmjd113.FoodOrder.repository;

import lk.ijse.cmjd113.FoodOrder.entity.FoodItemEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.FoodItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItemEntity, String> {
    @Query("SELECT f FROM FoodItemEntity f WHERE f.category.categoryId = :categoryId")
    List<FoodItemEntity> findByCategoryId(@Param("categoryId") String categoryId);

    List<FoodItemEntity> findByStatus(FoodItemStatus status);
    List<FoodItemEntity> findByNameContainingIgnoreCase(String name);
}
