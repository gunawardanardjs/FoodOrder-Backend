package lk.ijse.cmjd113.FoodOrder.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lk.ijse.cmjd113.FoodOrder.entity.enums.FoodItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodItemRequestDto implements Serializable {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    private String imageUrl;
    @NotNull
    private FoodItemStatus status;
    @NotNull
    private String categoryId;
}
