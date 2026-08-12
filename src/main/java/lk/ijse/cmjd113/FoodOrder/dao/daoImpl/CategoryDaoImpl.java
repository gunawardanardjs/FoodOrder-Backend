package lk.ijse.cmjd113.FoodOrder.dao.daoImpl;

import lk.ijse.cmjd113.FoodOrder.dao.CategoryDao;
import lk.ijse.cmjd113.FoodOrder.entity.CategoryEntity;
import lk.ijse.cmjd113.FoodOrder.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryDaoImpl implements CategoryDao {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) { return categoryRepository.save(categoryEntity); }
    @Override
    public Optional<CategoryEntity> findById(String categoryId) { return categoryRepository.findById(categoryId); }
    @Override
    public List<CategoryEntity> findAll() { return categoryRepository.findAll(); }
    @Override
    public void deleteById(String categoryId) { categoryRepository.deleteById(categoryId); }
    @Override
    public boolean existsByName(String name) { return categoryRepository.existsByName(name); }
}
