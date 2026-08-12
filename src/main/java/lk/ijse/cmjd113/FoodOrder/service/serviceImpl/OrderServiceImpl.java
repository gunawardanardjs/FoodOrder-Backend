package lk.ijse.cmjd113.FoodOrder.service.serviceImpl;

import jakarta.transaction.Transactional;
import lk.ijse.cmjd113.FoodOrder.dao.CartDao;
import lk.ijse.cmjd113.FoodOrder.dao.OrderDao;
import lk.ijse.cmjd113.FoodOrder.dao.UserDao;
import lk.ijse.cmjd113.FoodOrder.dto.request.OrderRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.OrderResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.OrderEntity;
import lk.ijse.cmjd113.FoodOrder.entity.OrderItemEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.OrderStatus;
import lk.ijse.cmjd113.FoodOrder.exception.BadRequestException;
import lk.ijse.cmjd113.FoodOrder.exception.DataNotFoundException;
import lk.ijse.cmjd113.FoodOrder.service.OrderService;
import lk.ijse.cmjd113.FoodOrder.util.IDGenerator;
import lk.ijse.cmjd113.FoodOrder.util.MappingDtoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final CartDao cartDao;
    private final MappingDtoEntity mapper;

    @Override
    public OrderResponseDto placeOrder(String userId, OrderRequestDto request) {
        var user = userDao.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found: " + userId));
        var cart = cartDao.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Cart not found for user: " + userId));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new BadRequestException("Cannot place order with an empty cart");
        }

        // Create order
        OrderEntity order = new OrderEntity();
        order.setOrderId(IDGenerator.orderIDGenerator());
        order.setUser(user);
        order.setTotalAmount(cart.getTotalPrice());
        order.setStatus(OrderStatus.PLACED);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setOrderItems(new ArrayList<>());

        // Convert cart items → order items
        cart.getCartItems().forEach(ci -> {
            OrderItemEntity oi = new OrderItemEntity();
            oi.setOrder(order);
            oi.setFoodItem(ci.getFoodItem());
            oi.setQuantity(ci.getQuantity());
            oi.setUnitPrice(ci.getUnitPrice()); // snapshot price at time of order
            order.getOrderItems().add(oi);
        });

        OrderEntity saved = orderDao.save(order);

        // Clear cart after successful order placement
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartDao.save(cart);

        log.info("Order placed: {} for user: {}", saved.getOrderId(), userId);
        return mapper.toOrderResponseDto(saved);
    }

    @Override
    public OrderResponseDto getOrderById(String orderId) {
        return mapper.toOrderResponseDto(
                orderDao.findById(orderId)
                        .orElseThrow(() -> new DataNotFoundException("Order not found: " + orderId))
        );
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(String userId) {
        return mapper.getOrderResponseDtoList(orderDao.findByUserId(userId));
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return mapper.getOrderResponseDtoList(orderDao.findAll());
    }

    @Override
    public OrderResponseDto updateOrderStatus(String orderId, OrderStatus status) {
        var order = orderDao.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found: " + orderId));
        order.setStatus(status);
        log.info("Order {} status updated to {}", orderId, status);
        return mapper.toOrderResponseDto(orderDao.save(order));
    }

    @Override
    public void cancelOrder(String orderId, String userId) {
        var order = orderDao.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found: " + orderId));

        if (!order.getUser().getUserId().equals(userId)) {
            throw new BadRequestException("You can only cancel your own orders");
        }
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new BadRequestException("Delivered orders cannot be cancelled");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderDao.save(order);
        log.info("Order cancelled: {}", orderId);
    }
}
