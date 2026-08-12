package lk.ijse.cmjd113.FoodOrder.service.serviceImpl;

import jakarta.transaction.Transactional;
import lk.ijse.cmjd113.FoodOrder.dao.OrderDao;
import lk.ijse.cmjd113.FoodOrder.dao.PaymentDao;
import lk.ijse.cmjd113.FoodOrder.dto.request.PaymentRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.PaymentResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.PaymentEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.PaymentStatus;
import lk.ijse.cmjd113.FoodOrder.exception.BadRequestException;
import lk.ijse.cmjd113.FoodOrder.exception.DataNotFoundException;
import lk.ijse.cmjd113.FoodOrder.service.PaymentService;
import lk.ijse.cmjd113.FoodOrder.util.IDGenerator;
import lk.ijse.cmjd113.FoodOrder.util.MappingDtoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentDao paymentDao;
    private final OrderDao orderDao;
    private final MappingDtoEntity mapper;

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto request) {
        var order = orderDao.findById(request.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found: " + request.getOrderId()));

        // Prevent double-payment
        paymentDao.findByOrderId(order.getOrderId()).ifPresent(p -> {
            if (p.getStatus() == PaymentStatus.COMPLETED) {
                throw new BadRequestException("Payment already completed for order: " + order.getOrderId());
            }
        });

        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(IDGenerator.paymentIDGenerator());
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(request.getTransactionId());

        PaymentEntity saved = paymentDao.save(payment);
        log.info("Payment processed: {} for order: {}", saved.getPaymentId(), order.getOrderId());
        return mapper.toPaymentResponseDto(saved);
    }

    @Override
    public PaymentResponseDto getPaymentByOrder(String orderId) {
        var payment = paymentDao.findByOrderId(orderId)
                .orElseThrow(() -> new DataNotFoundException("Payment not found for order: " + orderId));
        return mapper.toPaymentResponseDto(payment);
    }
}
