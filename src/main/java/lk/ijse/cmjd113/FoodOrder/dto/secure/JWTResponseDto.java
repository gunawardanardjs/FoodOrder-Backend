package lk.ijse.cmjd113.FoodOrder.dto.secure;

import lk.ijse.cmjd113.FoodOrder.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JWTResponseDto implements Serializable {
    private String token;
    private String userId;
    private String email;
    private String name;
    private Role role;
}
