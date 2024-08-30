package tbektenov.com.sau.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import java.time.LocalDateTime;

/**
 * Represents an order made by a doctor to a hospital pharmacy.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "issue_time", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime dateTimeOfIssue = LocalDateTime.now();

    @NotBlank(message = "Order cannot be blank.")
    @Column(name = "order_body", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private String orderBody;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @EqualsAndHashCode.Exclude
    private OrderStatus orderStatus = OrderStatus.ONGOING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HospitalPharmacy hospitalPharmacy;

    /**
     * Completes the given order by setting its status to COMPLETED.
     *
     * @param order the order to be completed
     * @return the updated order with its status set to COMPLETED
     * @throws InvalidArgumentsException if the order is null
     */
    public static OrderEntity finishOrder(OrderEntity order) {
        if (order != null) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            return order;
        } else {
            throw new InvalidArgumentsException("order is null.");
        }
    }
}
