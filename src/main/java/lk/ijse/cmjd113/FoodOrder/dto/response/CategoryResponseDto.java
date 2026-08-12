package lk.ijse.cmjd113.FoodOrder.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryResponseDto implements Serializable {
    private String categoryId;
    private String name;
    private String description;
    private String imageUrl;
    private int foodItemCount;
}
