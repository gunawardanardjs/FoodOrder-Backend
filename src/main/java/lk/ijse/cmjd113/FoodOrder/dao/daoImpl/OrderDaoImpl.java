package lk.ijse.cmjd113.FoodOrder.dao.daoImpl;

import lk.ijse.cmjd113.FoodOrder.dao.OrderDao;
import lk.ijse.cmjd113.FoodOrder.entity.OrderEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.OrderStatus;
import lk.ijse.cmjd113.FoodOrder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private final OrderRepository orderRepository;

    @Override
    public OrderEntity save(OrderEntity orderEntity) { return orderRepository.save(orderEntity); }
    @Override
    public Optional<OrderEntity> findById(String orderId) { return orderRepository.findById(orderId); }
    @Override
    public List<OrderEntity> findByUserId(String userId) { return orderRepository.findByUserIdOrderByOrderDateDesc(userId); }
    @Override
    public List<OrderEntity> findAll() { return orderRepository.findAll(); }
    @Override
    public List<OrderEntity> findByStatus(OrderStatus status) { return orderRepository.findByStatus(status); }
    @Override
    public void deleteById(String orderId) { orderRepository.deleteById(orderId); }
}
