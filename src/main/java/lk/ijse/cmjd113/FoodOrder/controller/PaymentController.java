package lk.ijse.cmjd113.FoodOrder.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd113.FoodOrder.dto.request.PaymentRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.PaymentResponseDto;
import lk.ijse.cmjd113.FoodOrder.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    /* POST /foodorder/api/v1/payments — process payment for an order */
    @PostMapping
    public ResponseEntity<PaymentResponseDto> processPayment(@Valid @RequestBody PaymentRequestDto request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    /* GET /foodorder/api/v1/payments/order/{orderId} — get payment by order */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId));
    }
}
