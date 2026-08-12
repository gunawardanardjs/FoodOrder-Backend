package lk.ijse.cmjd113.FoodOrder.dto.response;

import lk.ijse.cmjd113.FoodOrder.entity.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto implements Serializable {
    private String orderId;
    private String userId;
    private String userName;
    private List<OrderItemResponseDto> orderItems;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private String deliveryAddress;
    private PaymentResponseDto payment;
}
