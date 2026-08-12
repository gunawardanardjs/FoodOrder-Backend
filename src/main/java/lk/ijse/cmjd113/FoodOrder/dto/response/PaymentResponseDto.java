package lk.ijse.cmjd113.FoodOrder.dto.response;

import lk.ijse.cmjd113.FoodOrder.entity.enums.PaymentStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDto implements Serializable {
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String transactionId;
}
