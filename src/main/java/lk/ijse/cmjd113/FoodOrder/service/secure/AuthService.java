package lk.ijse.cmjd113.FoodOrder.service.secure;

import lk.ijse.cmjd113.FoodOrder.dto.request.SignUpRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.secure.JWTResponseDto;
import lk.ijse.cmjd113.FoodOrder.dto.secure.LoginDto;

public interface AuthService {
    JWTResponseDto login(LoginDto loginDto);
    JWTResponseDto register(SignUpRequestDto request);
}
