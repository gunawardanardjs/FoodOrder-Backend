package lk.ijse.cmjd113.FoodOrder.service;

import lk.ijse.cmjd113.FoodOrder.dto.request.CartItemRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.CartResponseDto;

public interface CartService {
    CartResponseDto getCartByUser(String userId);
    CartResponseDto addItemToCart(String userId, CartItemRequestDto request);
    CartResponseDto updateCartItem(String userId, Long cartItemId, Integer quantity);
    CartResponseDto removeCartItem(String userId, Long cartItemId);
    void clearCart(String userId);
}
