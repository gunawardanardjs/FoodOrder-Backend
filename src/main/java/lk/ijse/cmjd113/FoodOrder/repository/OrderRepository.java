package lk.ijse.cmjd113.FoodOrder.repository;

import lk.ijse.cmjd113.FoodOrder.entity.OrderEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    @Query("SELECT o FROM OrderEntity o WHERE o.user.userId = :userId")
    List<OrderEntity> findByUserId(@Param("userId") String userId);

    List<OrderEntity> findByStatus(OrderStatus status);

    @Query("SELECT o FROM OrderEntity o WHERE o.user.userId = :userId ORDER BY o.orderDate DESC")
    List<OrderEntity> findByUserIdOrderByOrderDateDesc(@Param("userId") String userId);
}
