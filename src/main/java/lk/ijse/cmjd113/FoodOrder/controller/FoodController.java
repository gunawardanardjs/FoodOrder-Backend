package lk.ijse.cmjd113.FoodOrder.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd113.FoodOrder.dto.request.FoodItemRequestDto;
import lk.ijse.cmjd113.FoodOrder.dto.response.FoodItemResponseDto;
import lk.ijse.cmjd113.FoodOrder.service.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodItemService foodItemService;

    /**
     * GET /foodorder/api/v1/foods — Public
     * Optional query params: ?categoryId=CAT-xxx  or  ?search=pizza
     */
    @GetMapping
    public ResponseEntity<List<FoodItemResponseDto>> getAllFoods(
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String search) {

        if (categoryId != null && !categoryId.isBlank()) {
            return ResponseEntity.ok(foodItemService.getFoodItemsByCategory(categoryId));
        }
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(foodItemService.searchFoodItems(search));
        }
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    /* GET /foodorder/api/v1/foods/{foodItemId} — Public */
    @GetMapping("/{foodItemId}")
    public ResponseEntity<FoodItemResponseDto> getFoodById(@PathVariable String foodItemId) {
        return ResponseEntity.ok(foodItemService.getFoodItemById(foodItemId));
    }

    /* POST /foodorder/api/v1/foods — ADMIN only */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodItemResponseDto> createFood(@Valid @RequestBody FoodItemRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodItemService.createFoodItem(request));
    }

    /* PUT /foodorder/api/v1/foods/{foodItemId} — ADMIN only */
    @PutMapping("/{foodItemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodItemResponseDto> updateFood(@PathVariable String foodItemId,
                                                          @Valid @RequestBody FoodItemRequestDto request) {
        return ResponseEntity.ok(foodItemService.updateFoodItem(foodItemId, request));
    }

    /* DELETE /foodorder/api/v1/foods/{foodItemId} — ADMIN only */
    @DeleteMapping("/{foodItemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFood(@PathVariable String foodItemId) {
        foodItemService.deleteFoodItem(foodItemId);
        return ResponseEntity.ok("Food item deleted successfully");
    }
}
