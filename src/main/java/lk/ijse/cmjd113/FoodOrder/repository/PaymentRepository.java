package lk.ijse.cmjd113.FoodOrder.repository;

import lk.ijse.cmjd113.FoodOrder.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    @Query("SELECT p FROM PaymentEntity p WHERE p.order.orderId = :orderId")
    Optional<PaymentEntity> findByOrderId(@Param("orderId") String orderId);
}
