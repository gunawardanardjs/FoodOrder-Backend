package lk.ijse.cmjd113.FoodOrder.dao.daoImpl;

import lk.ijse.cmjd113.FoodOrder.dao.CartDao;
import lk.ijse.cmjd113.FoodOrder.entity.CartEntity;
import lk.ijse.cmjd113.FoodOrder.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartDaoImpl implements CartDao {
    private final CartRepository cartRepository;

    @Override
    public CartEntity save(CartEntity cartEntity) { return cartRepository.save(cartEntity); }
    @Override
    public Optional<CartEntity> findById(Long cartId) { return cartRepository.findById(cartId); }
    @Override
    public Optional<CartEntity> findByUserId(String  userId) { return cartRepository.findByUserId(userId); }
    @Override
    public void deleteById(Long cartId) { cartRepository.deleteById(cartId); }
}
