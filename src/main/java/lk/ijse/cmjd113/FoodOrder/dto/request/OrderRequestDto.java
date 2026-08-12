package lk.ijse.cmjd113.FoodOrder.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDto implements Serializable {
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;
}
