package tbektenov.com.sau.models.pharmacy;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Hospital Pharmacy.
 */
@Data
@Entity
@Table(name = "HOSPITAL_PHARMACY")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalPharmacy extends Pharmacy {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @OneToMany(mappedBy = "hospitalPharmacy", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderEntity> orders = new HashSet<>();
}
