package lk.ijse.cmjd113.FoodOrder.dao;

import lk.ijse.cmjd113.FoodOrder.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(String userId);
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAll();
    void deleteById(String userId);
    boolean existsByEmail(String email);
}
