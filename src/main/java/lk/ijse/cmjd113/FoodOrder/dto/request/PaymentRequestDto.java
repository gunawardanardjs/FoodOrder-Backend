package lk.ijse.cmjd113.FoodOrder.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequestDto implements Serializable {
    @NotNull
    private String orderId;
    private String transactionId;
}
