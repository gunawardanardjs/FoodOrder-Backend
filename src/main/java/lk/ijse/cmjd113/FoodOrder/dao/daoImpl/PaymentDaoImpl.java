package lk.ijse.cmjd113.FoodOrder.dao.daoImpl;

import lk.ijse.cmjd113.FoodOrder.dao.PaymentDao;
import lk.ijse.cmjd113.FoodOrder.entity.PaymentEntity;
import lk.ijse.cmjd113.FoodOrder.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentDaoImpl implements PaymentDao {
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentEntity save(PaymentEntity paymentEntity) { return paymentRepository.save(paymentEntity); }
    @Override
    public Optional<PaymentEntity> findById(String paymentId) { return paymentRepository.findById(paymentId); }
    @Override
    public Optional<PaymentEntity> findByOrderId(String orderId) { return paymentRepository.findByOrderId(orderId); }
}
