package lk.ijse.cmjd113.FoodOrder.service.serviceImpl;

import jakarta.transaction.Transactional;
import lk.ijse.cmjd113.FoodOrder.dao.CategoryDao;
import lk.ijse.cmjd113.FoodOrder.dao.FoodItemDao;
import lk.ijse.cmjd113.FoodOrder.dto.request.FoodItemRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.FoodItemResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.FoodItemEntity;
import lk.ijse.cmjd113.FoodOrder.exception.DataNotFoundException;
import lk.ijse.cmjd113.FoodOrder.service.FoodItemService;
import lk.ijse.cmjd113.FoodOrder.util.IDGenerator;
import lk.ijse.cmjd113.FoodOrder.util.MappingDtoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemDao foodItemDao;
    private final CategoryDao categoryDao;
    private final MappingDtoEntity mapper;

    @Override
    public FoodItemResponseDto createFoodItem(FoodItemRequestDto request) {
        var category = categoryDao.findById(request.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found: " + request.getCategoryId()));

        FoodItemEntity entity = new FoodItemEntity();
        entity.setFoodItemId(IDGenerator.foodIDGenerator());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setImageUrl(request.getImageUrl());
        entity.setStatus(request.getStatus());
        entity.setCategory(category);

        return mapper.toFoodItemResponseDto(foodItemDao.save(entity));
    }

    @Override
    public FoodItemResponseDto updateFoodItem(String foodItemId, FoodItemRequestDto request) {
        var entity = foodItemDao.findById(foodItemId)
                .orElseThrow(() -> new DataNotFoundException("Food item not found: " + foodItemId));
        var category = categoryDao.findById(request.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found: " + request.getCategoryId()));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setImageUrl(request.getImageUrl());
        entity.setStatus(request.getStatus());
        entity.setCategory(category);

        return mapper.toFoodItemResponseDto(foodItemDao.save(entity));
    }

    @Override
    public FoodItemResponseDto getFoodItemById(String foodItemId) {
        return mapper.toFoodItemResponseDto(
                foodItemDao.findById(foodItemId)
                        .orElseThrow(() -> new DataNotFoundException("Food item not found: " + foodItemId))
        );
    }

    @Override
    public List<FoodItemResponseDto> getAllFoodItems() {
        return mapper.getFoodItemResponseDtoList(foodItemDao.findAll());
    }

    @Override
    public List<FoodItemResponseDto> getFoodItemsByCategory(String categoryId) {
        return mapper.getFoodItemResponseDtoList(foodItemDao.findByCategoryId(categoryId));
    }

    @Override
    public List<FoodItemResponseDto> searchFoodItems(String name) {
        return mapper.getFoodItemResponseDtoList(foodItemDao.searchByName(name));
    }

    @Override
    public void deleteFoodItem(String foodItemId) {
        foodItemDao.findById(foodItemId)
                .orElseThrow(() -> new DataNotFoundException("Food item not found: " + foodItemId));
        foodItemDao.deleteById(foodItemId);
        log.info("Food item deleted: {}", foodItemId);
    }
}
