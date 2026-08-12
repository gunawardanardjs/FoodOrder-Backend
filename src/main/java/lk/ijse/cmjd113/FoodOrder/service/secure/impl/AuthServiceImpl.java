package lk.ijse.cmjd113.FoodOrder.service.secure.impl;

import jakarta.transaction.Transactional;
import lk.ijse.cmjd113.FoodOrder.dao.CartDao;
import lk.ijse.cmjd113.FoodOrder.dao.UserDao;
import lk.ijse.cmjd113.FoodOrder.dto.request.SignUpRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.secure.JWTResponseDto;
import lk.ijse.cmjd113.FoodOrder.dto.secure.LoginDto;
import lk.ijse.cmjd113.FoodOrder.entity.CartEntity;
import lk.ijse.cmjd113.FoodOrder.entity.UserEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.Role;
import lk.ijse.cmjd113.FoodOrder.exception.BadRequestException;
import lk.ijse.cmjd113.FoodOrder.exception.DuplicateResourceException;
import lk.ijse.cmjd113.FoodOrder.securityConfig.JWTUtils;
import lk.ijse.cmjd113.FoodOrder.service.secure.AuthService;
import lk.ijse.cmjd113.FoodOrder.util.IDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final JWTUtils jwtUtils;
    private final UserDao userDao;
    private final CartDao cartDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public JWTResponseDto login(LoginDto loginDTO) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    ));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid email or password");
        }

        //Fetch user
        var authUser = userDao.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //Token generate
        var token = jwtUtils.generateToken(
                authUser.getUsername(),
                authUser.getAuthorities()

        );

        log.info("User logged in: {}", authUser.getEmail());
        return JWTResponseDto.builder()
                .token(token)
                .userId(authUser.getUserId())
                .email(authUser.getEmail())
                .name(authUser.getName())
                .role(authUser.getRole())
                .build();
    }

    @Override
    public JWTResponseDto register(SignUpRequestDto request) {
        if(userDao.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("User Already exist");
        }

        // Build and save user entity
        UserEntity user  = new UserEntity();
        user.setUserId(IDGenerator.userIDGenerator());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(Role.CUSTOMER); // Always register as Customer

        UserEntity savedUser = userDao.save(user);

        // Auto-create an empty cart for the new customer
        CartEntity cart = new CartEntity();
        cart.setUser(savedUser);
        cart.setCartItems(new ArrayList<>());
        cart.setTotalPrice(BigDecimal.ZERO);
        cartDao.save(cart);

        //Generate ID
        var token = jwtUtils.generateToken(
                savedUser.getEmail(),
                savedUser.getAuthorities()

        );

        log.info("New user registered: {}", savedUser.getEmail());
        return JWTResponseDto.builder()
                .token(token)
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .build();

    }
}
