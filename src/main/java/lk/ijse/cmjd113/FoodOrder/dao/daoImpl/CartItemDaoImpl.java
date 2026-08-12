package lk.ijse.cmjd113.FoodOrder.dao.daoImpl;

import lk.ijse.cmjd113.FoodOrder.dao.CartItemDao;
import lk.ijse.cmjd113.FoodOrder.entity.CartItemEntity;
import lk.ijse.cmjd113.FoodOrder.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemDaoImpl implements CartItemDao {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemEntity save(CartItemEntity cartItem) {
        return cartItemRepository.save(cartItem);
    }
    @Override
    public Optional<CartItemEntity> findById(Long id) {
        return cartItemRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
