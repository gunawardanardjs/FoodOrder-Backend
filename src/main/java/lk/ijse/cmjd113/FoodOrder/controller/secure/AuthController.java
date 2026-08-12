package lk.ijse.cmjd113.FoodOrder.controller.secure;

import jakarta.validation.Valid;
import lk.ijse.cmjd113.FoodOrder.dto.request.SignUpRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.secure.JWTResponseDto;
import lk.ijse.cmjd113.FoodOrder.dto.secure.LoginDto;
import lk.ijse.cmjd113.FoodOrder.service.secure.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /* POST /foodorder/api/v1/auth/login */
    @PostMapping("/login")
    public ResponseEntity<JWTResponseDto> login(@Valid @RequestBody LoginDto loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    /* POST /foodorder/api/v1/auth/signup */
    @PostMapping("/signup")
    public ResponseEntity<JWTResponseDto> signUp(@Valid @RequestBody SignUpRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}
