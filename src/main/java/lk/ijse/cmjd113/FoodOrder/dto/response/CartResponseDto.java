package lk.ijse.cmjd113.FoodOrder.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDto implements Serializable {
    private Long cartId;
    private String userId;
    private List<CartItemResponseDto> items;
    private BigDecimal totalPrice;
    private int totalItems;
}
