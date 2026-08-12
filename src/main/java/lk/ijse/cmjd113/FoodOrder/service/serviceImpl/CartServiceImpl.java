package lk.ijse.cmjd113.FoodOrder.service.serviceImpl;

import jakarta.transaction.Transactional;
import lk.ijse.cmjd113.FoodOrder.dao.CartDao;
import lk.ijse.cmjd113.FoodOrder.dao.FoodItemDao;
import lk.ijse.cmjd113.FoodOrder.dao.UserDao;
import lk.ijse.cmjd113.FoodOrder.dto.request.CartItemRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.CartResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.CartEntity;
import lk.ijse.cmjd113.FoodOrder.entity.CartItemEntity;
import lk.ijse.cmjd113.FoodOrder.exception.DataNotFoundException;
import lk.ijse.cmjd113.FoodOrder.service.CartService;
import lk.ijse.cmjd113.FoodOrder.util.MappingDtoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final UserDao userDao;
    private final FoodItemDao foodItemDao;
    private final MappingDtoEntity mapper;

    @Override
    public CartResponseDto getCartByUser(String userId) {
        return mapper.toCartResponseDto(getOrCreateCart(userId));
    }

    @Override
    public CartResponseDto addItemToCart(String userId, CartItemRequestDto request) {
        var cart = getOrCreateCart(userId);
        var foodItem = foodItemDao.findById(request.getFoodItemId())
                .orElseThrow(() -> new DataNotFoundException("Food item not found: " + request.getFoodItemId()));

        // If item already exists in cart — increase quantity
        var existing = cart.getCartItems().stream()
                .filter(ci -> ci.getFoodItem().getFoodItemId().equals(foodItem.getFoodItemId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + request.getQuantity());
        } else {
            CartItemEntity item = new CartItemEntity();
            item.setCart(cart);
            item.setFoodItem(foodItem);
            item.setQuantity(request.getQuantity());
            item.setUnitPrice(foodItem.getPrice());
            cart.getCartItems().add(item);
        }

        cart.recalculateTotal();
        return mapper.toCartResponseDto(cartDao.save(cart));
    }

    @Override
    public CartResponseDto updateCartItem(String userId, Long cartItemId, Integer quantity) {
        var cart = getOrCreateCart(userId);
        var item = cart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Cart item not found: " + cartItemId));

        if (quantity <= 0) {
            cart.getCartItems().remove(item);
        } else {
            item.setQuantity(quantity);
        }

        cart.recalculateTotal();
        return mapper.toCartResponseDto(cartDao.save(cart));
    }

    @Override
    public CartResponseDto removeCartItem(String userId, Long cartItemId) {
        var cart = getOrCreateCart(userId);
        cart.getCartItems().removeIf(ci -> ci.getId().equals(cartItemId));
        cart.recalculateTotal();
        return mapper.toCartResponseDto(cartDao.save(cart));
    }

    @Override
    public void clearCart(String userId) {
        var cart = getOrCreateCart(userId);
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartDao.save(cart);
        log.info("Cart cleared for user: {}", userId);
    }

    /* Finds existing cart or creates a fresh one for the user. */
    private CartEntity getOrCreateCart(String userId) {
        return cartDao.findByUserId(userId).orElseGet(() -> {
            var user = userDao.findById(userId)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + userId));
            CartEntity newCart = new CartEntity();
            newCart.setUser(user);
            newCart.setCartItems(new ArrayList<>());
            newCart.setTotalPrice(BigDecimal.ZERO);
            return cartDao.save(newCart);
        });
    }
}
