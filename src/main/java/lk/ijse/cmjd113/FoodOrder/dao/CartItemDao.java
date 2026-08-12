package lk.ijse.cmjd113.FoodOrder.dao;

import lk.ijse.cmjd113.FoodOrder.entity.CartItemEntity;

import java.util.Optional;

public interface CartItemDao {
    CartItemEntity save(CartItemEntity cartItem);
    Optional<CartItemEntity> findById(Long id);
    void deleteById(Long id);
}
