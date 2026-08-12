package lk.ijse.cmjd113.FoodOrder.dao;

import lk.ijse.cmjd113.FoodOrder.entity.OrderEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    OrderEntity save(OrderEntity order);
    Optional<OrderEntity> findById(String orderId);
    List<OrderEntity> findByUserId(String userId);
    List<OrderEntity> findAll();
    List<OrderEntity> findByStatus(OrderStatus status);
    void deleteById(String orderId);
}
