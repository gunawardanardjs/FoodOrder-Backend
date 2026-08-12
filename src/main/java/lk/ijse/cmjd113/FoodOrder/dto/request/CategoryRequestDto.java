package lk.ijse.cmjd113.FoodOrder.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequestDto implements Serializable {
    @NotBlank(message = "Category name is required")
    private String name;
    private String description;
    private String imageUrl;
}
