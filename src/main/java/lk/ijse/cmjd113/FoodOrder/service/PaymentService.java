package lk.ijse.cmjd113.FoodOrder.service;

import lk.ijse.cmjd113.FoodOrder.dto.request.PaymentRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto request);
    PaymentResponseDto getPaymentByOrder(String orderId);
}
