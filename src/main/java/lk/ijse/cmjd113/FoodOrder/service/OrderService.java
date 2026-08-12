package lk.ijse.cmjd113.FoodOrder.service;

import lk.ijse.cmjd113.FoodOrder.dto.request.OrderRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.OrderResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(String userId, OrderRequestDto request);
    OrderResponseDto getOrderById(String orderId);
    List<OrderResponseDto> getOrdersByUser(String userId);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto updateOrderStatus(String orderId, OrderStatus status);
    void cancelOrder(String orderId, String userId);
}
