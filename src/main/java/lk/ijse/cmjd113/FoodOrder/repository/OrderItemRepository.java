package lk.ijse.cmjd113.FoodOrder.repository;

import lk.ijse.cmjd113.FoodOrder.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    @Query("SELECT oi FROM OrderItemEntity oi WHERE oi.order.orderId = :orderId")
    List<OrderItemEntity> findByOrderId(@Param("orderId") String orderId);
}
