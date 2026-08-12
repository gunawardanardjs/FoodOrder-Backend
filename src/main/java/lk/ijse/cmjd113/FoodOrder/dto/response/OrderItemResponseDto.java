package lk.ijse.cmjd113.FoodOrder.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItemResponseDto implements Serializable {
    private Long orderItemId;
    private String foodItemId;
    private String foodItemName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
