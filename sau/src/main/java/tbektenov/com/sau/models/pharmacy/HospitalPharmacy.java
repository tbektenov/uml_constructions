package tbektenov.com.sau.models.pharmacy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.query.Order;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.OrderStatus;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a hospital pharmacy associated with a specific hospital.
 */
@Data
@Entity
@Table(name = "HOSPITAL_PHARMACY")
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "HP.orderDetails",
                attributeNodes = {
                        @NamedAttributeNode("hospital"),
                        @NamedAttributeNode("orders")
                }
        )
)
public class HospitalPharmacy
        extends Pharmacy {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @OneToMany(mappedBy = "hospitalPharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderEntity> orders = new ArrayList<>();

    /**
     * Constructs a new HospitalPharmacy with the specified name, type, and associated hospital.
     *
     * @param name              the name of the pharmacy
     * @param isCompoundPharmacy whether the pharmacy is a compound pharmacy
     * @param hospital          the hospital to which this pharmacy belongs
     */
    @Builder
    public HospitalPharmacy(String name,
                            boolean isCompoundPharmacy,
                            Hospital hospital) {
        this.name = name;
        this.isCompoundPharmacy = isCompoundPharmacy;
        this.hospital = hospital;
    }

    public String getAddress() {
        return hospital.getAddress();
    }

    public void addOrder(OrderEntity order) {
        if (order == null) {
            throw new InvalidArgumentsException("Order cannot be null.");
        }

        if (!orders.contains(order)) {
            orders.add(order);
            order.setHospitalPharmacy(this);
        }
    }

    public OrderEntity finishOrder(OrderEntity order) {
        if (order == null) {
            throw new InvalidArgumentsException("order is null");
        }

        if (this.orders.contains(order)) {
            order.setOrderStatus(OrderStatus.COMPLETED);

            return order;
        } else {
            throw new InvalidArgumentsException("Order is not assigned to this hospital pharmacy");
        }
    }
}
