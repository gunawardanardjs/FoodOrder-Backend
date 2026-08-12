package lk.ijse.cmjd113.FoodOrder.dto.response;

import lk.ijse.cmjd113.FoodOrder.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Role role;
}
