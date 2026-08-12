package lk.ijse.cmjd113.FoodOrder.dto.secure;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto implements Serializable {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
