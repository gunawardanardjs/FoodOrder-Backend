package lk.ijse.cmjd113.FoodOrder.entity;

import jakarta.persistence.*;
import lk.ijse.cmjd113.FoodOrder.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payments")
public class PaymentEntity implements Serializable {
    @Id
    private String paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true, nullable = false)
    private OrderEntity order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private LocalDateTime paymentDate;

    /** External payment gateway transaction reference. */
    private String transactionId;

    @PrePersist
    public void prePersist() {
        if (this.status == null) this.status = PaymentStatus.PENDING;
    }
}
