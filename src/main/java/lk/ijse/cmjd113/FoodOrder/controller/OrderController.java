package lk.ijse.cmjd113.FoodOrder.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd113.FoodOrder.dto.request.OrderRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.OrderResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.UserEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.OrderStatus;
import lk.ijse.cmjd113.FoodOrder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /* POST /foodorder/api/v1/orders — place a new order from cart */
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@AuthenticationPrincipal UserEntity currentUser,
                                                       @Valid @RequestBody OrderRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(currentUser.getUserId(), request));
    }

    /* GET /foodorder/api/v1/orders/my — current user's orders */
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(orderService.getOrdersByUser(currentUser.getUserId()));
    }

    /* GET /foodorder/api/v1/orders/{orderId} — get single order */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    /* GET /foodorder/api/v1/orders — all orders (ADMIN only) */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /* PUT /foodorder/api/v1/orders/{orderId}/status?status=PREPARING — ADMIN only */
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable String orderId,
                                                              @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    /* PUT /foodorder/api/v1/orders/{orderId}/cancel — cancel own order */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@AuthenticationPrincipal UserEntity currentUser,
                                              @PathVariable String orderId) {
        orderService.cancelOrder(orderId, currentUser.getUserId());
        return ResponseEntity.ok("Order cancelled successfully");
    }
}
