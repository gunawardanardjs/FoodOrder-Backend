package lk.ijse.cmjd113.FoodOrder.util;

import lk.ijse.cmjd113.FoodOrder.dto.response.*;
import lk.ijse.cmjd113.FoodOrder.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Central mapping utility between entity and DTO.
 * Complex nested entities are mapped manually to avoid ModelMapper circular-reference issues.
 */

@Component
@RequiredArgsConstructor
public class MappingDtoEntity {
    private final ModelMapper modelMapper;

    // User
    public UserDto toUserDto(UserEntity userEntity) {
        // Manual mapping - skip 'cart' to avoid infinite recursion
        UserDto dto = new UserDto();
        dto.setUserId(userEntity.getUserId());
        dto.setName(userEntity.getName());
        dto.setEmail(userEntity.getEmail());
        dto.setPhone(userEntity.getPhone());
        dto.setAddress(userEntity.getAddress());
        dto.setRole(userEntity.getRole());
        // Note: Password intentionally not mapped into response
        return dto;
    }

    public UserEntity toUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    public List<UserDto> getUserDtoList(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(this::toUserDto).toList();
    }

    // Food Item
    public FoodItemResponseDto toFoodItemResponseDto(FoodItemEntity entity) {
        FoodItemResponseDto dto = new FoodItemResponseDto();
        dto.setFoodItemId(entity.getFoodItemId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getImageUrl());
        dto.setStatus(entity.getStatus());
        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getCategoryId());
            dto.setCategoryName(entity.getCategory().getName());
        }
        return dto;
    }

    public List<FoodItemResponseDto> getFoodItemResponseDtoList(List<FoodItemEntity> list) {
        return list.stream().map(this::toFoodItemResponseDto).toList();
    }

    // Category
    public CategoryResponseDto toCategoryResponseDto(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setCategoryId(entity.getCategoryId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setFoodItemCount(entity.getFoodItems() == null ? 0 : entity.getFoodItems().size());
        return dto;
    }

    public List<CategoryResponseDto> getCategoryResponseDtoList(List<CategoryEntity> list) {
        return list.stream().map(this::toCategoryResponseDto).toList();
    }

    // Cart Item
    public CartItemResponseDto toCartItemResponseDto(CartItemEntity entity) {
        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(entity.getId());
        if (entity.getFoodItem() != null) {
            dto.setFoodItemId(entity.getFoodItem().getFoodItemId());
            dto.setFoodItemName(entity.getFoodItem().getName());
            dto.setFoodItemImage(entity.getFoodItem().getImageUrl());
        }
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }

    // Cart
    public CartResponseDto toCartResponseDto(CartEntity entity) {
        CartResponseDto dto = new CartResponseDto();
        dto.setCartId(entity.getCartId());
        if (entity.getUser() != null) dto.setUserId(entity.getUser().getUserId());
        List<CartItemResponseDto> items = entity.getCartItems() == null
                ? List.of()
                : entity.getCartItems().stream().map(this::toCartItemResponseDto).toList();
        dto.setItems(items);
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setTotalItems(items.size());
        return dto;
    }

    // Order Item
    public OrderItemResponseDto toOrderItemResponseDto(OrderItemEntity entity) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setOrderItemId(entity.getId());
        if (entity.getFoodItem() != null) {
            dto.setFoodItemId(entity.getFoodItem().getFoodItemId());
            dto.setFoodItemName(entity.getFoodItem().getName());
        }
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }

    // Order
    public OrderResponseDto toOrderResponseDto(OrderEntity entity) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(entity.getOrderId());
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getUserId());
            dto.setUserName(entity.getUser().getName());
        }
        List<OrderItemResponseDto> items = entity.getOrderItems() == null
                ? List.of()
                : entity.getOrderItems().stream().map(this::toOrderItemResponseDto).toList();
        dto.setOrderItems(items);
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setStatus(entity.getStatus());
        dto.setOrderDate(entity.getOrderDate());
        dto.setDeliveryAddress(entity.getDeliveryAddress());
        if (entity.getPayment() != null) {
            dto.setPayment(toPaymentResponseDto(entity.getPayment()));
        }
        return dto;
    }

    public List<OrderResponseDto> getOrderResponseDtoList(List<OrderEntity> list) {
        return list.stream().map(this::toOrderResponseDto).toList();
    }

    // Payment
    public PaymentResponseDto toPaymentResponseDto(PaymentEntity entity) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setPaymentId(entity.getPaymentId());
        if (entity.getOrder() != null) dto.setOrderId(entity.getOrder().getOrderId());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setTransactionId(entity.getTransactionId());
        return dto;
    }
}
