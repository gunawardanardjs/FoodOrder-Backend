package lk.ijse.cmjd113.FoodOrder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemResponseDto implements Serializable {
    private Long id;
    private String foodItemId;
    private String foodItemName;
    private String foodItemImage;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
