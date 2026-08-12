package lk.ijse.cmjd113.FoodOrder.dao;

import lk.ijse.cmjd113.FoodOrder.entity.CartEntity;

import java.util.Optional;

public interface CartDao {
    CartEntity save(CartEntity cart);
    Optional<CartEntity> findById(Long cartId);
    Optional<CartEntity> findByUserId(String userId);
    void deleteById(Long cartId);
}
