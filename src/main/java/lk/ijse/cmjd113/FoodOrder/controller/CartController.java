package lk.ijse.cmjd113.FoodOrder.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd113.FoodOrder.dto.request.CartItemRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.CartResponseDto;
import lk.ijse.cmjd113.FoodOrder.entity.UserEntity;
import lk.ijse.cmjd113.FoodOrder.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /* GET /foodorder/api/v1/cart — get current user's cart */
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(cartService.getCartByUser(currentUser.getUserId()));
    }

    /* POST /foodorder/api/v1/cart/add — add item to cart */
    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(@AuthenticationPrincipal UserEntity currentUser,
                                                     @Valid @RequestBody CartItemRequestDto request) {
        return ResponseEntity.ok(cartService.addItemToCart(currentUser.getUserId(), request));
    }

    /* PUT /foodorder/api/v1/cart/items/{cartItemId}?quantity=3 — update quantity */
    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponseDto> updateCartItem(@AuthenticationPrincipal UserEntity currentUser,
                                                          @PathVariable Long cartItemId,
                                                          @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(currentUser.getUserId(), cartItemId, quantity));
    }

    /* DELETE /foodorder/api/v1/cart/items/{cartItemId} — remove one item */
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponseDto> removeCartItem(@AuthenticationPrincipal UserEntity currentUser,
                                                          @PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeCartItem(currentUser.getUserId(), cartItemId));
    }

    /* DELETE /foodorder/api/v1/cart/clear — clear entire cart */
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@AuthenticationPrincipal UserEntity currentUser) {
        cartService.clearCart(currentUser.getUserId());
        return ResponseEntity.ok("Cart cleared");
    }
}
