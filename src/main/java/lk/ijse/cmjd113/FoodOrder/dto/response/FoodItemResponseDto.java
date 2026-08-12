package lk.ijse.cmjd113.FoodOrder.dto.response;

import lk.ijse.cmjd113.FoodOrder.entity.enums.FoodItemStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FoodItemResponseDto implements Serializable {
    private String foodItemId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private FoodItemStatus status;
    private String categoryId;
    private String categoryName;
}
