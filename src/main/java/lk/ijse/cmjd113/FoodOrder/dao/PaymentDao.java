package lk.ijse.cmjd113.FoodOrder.dao;

import lk.ijse.cmjd113.FoodOrder.entity.PaymentEntity;

import java.util.Optional;

public interface PaymentDao {
    PaymentEntity save(PaymentEntity payment);
    Optional<PaymentEntity> findById(String paymentId);
    Optional<PaymentEntity> findByOrderId(String orderId);
}
