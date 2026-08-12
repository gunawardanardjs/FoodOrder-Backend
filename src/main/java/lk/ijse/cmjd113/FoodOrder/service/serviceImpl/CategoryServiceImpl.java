package lk.ijse.cmjd113.FoodOrder.service.serviceImpl;

import jakarta.transaction.Transactional;
import lk.ijse.cmjd113.FoodOrder.dao.CategoryDao;
import lk.ijse.cmjd113.FoodOrder.dto.request.CategoryRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.CategoryResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.CategoryEntity;
import lk.ijse.cmjd113.FoodOrder.exception.DataNotFoundException;
import lk.ijse.cmjd113.FoodOrder.exception.DuplicateResourceException;
import lk.ijse.cmjd113.FoodOrder.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final MappingDtoEntity mapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        if (categoryDao.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category already exists: " + request.getName());
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryId(IDGenerator.categoryIDGenerator());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setImageUrl(request.getImageUrl());

        return mapper.toCategoryResponseDto(categoryDao.save(entity));
    }

    @Override
    public CategoryResponseDto updateCategory(String categoryId, CategoryRequestDto request) {
        var entity = categoryDao.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category not found: " + categoryId));
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setImageUrl(request.getImageUrl());
        return mapper.toCategoryResponseDto(categoryDao.save(entity));
    }

    @Override
    public CategoryResponseDto getCategoryById(String categoryId) {
        var entity = categoryDao.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category not found: " + categoryId));
        return mapper.toCategoryResponseDto(entity);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return mapper.getCategoryResponseDtoList(categoryDao.findAll());
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryDao.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category not found: " + categoryId));
        categoryDao.deleteById(categoryId);
        log.info("Category deleted: {}", categoryId);
    }
}
