package lk.ijse.cmjd113.FoodOrder.dao.daoImpl;

import lk.ijse.cmjd113.FoodOrder.dao.FoodItemDao;
import lk.ijse.cmjd113.FoodOrder.entity.FoodItemEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.FoodItemStatus;
import lk.ijse.cmjd113.FoodOrder.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FoodItemDaoImpl implements FoodItemDao {
    private final FoodItemRepository repo;

    @Override
    public FoodItemEntity save(FoodItemEntity foodItem) { return repo.save(foodItem); }
    @Override
    public Optional<FoodItemEntity> findById(String foodItemId) { return repo.findById(foodItemId); }
    @Override
    public List<FoodItemEntity> findAll() { return repo.findAll(); }
    @Override
    public List<FoodItemEntity> findByCategoryId(String categoryId) { return repo.findByCategoryId(categoryId); }
    @Override
    public List<FoodItemEntity> findByStatus(FoodItemStatus status) { return repo.findByStatus(status); }
    @Override
    public List<FoodItemEntity> searchByName(String n) { return repo.findByNameContainingIgnoreCase(n); }
    @Override
    public void deleteById(String foodItemId) { repo.deleteById(foodItemId); }
}
