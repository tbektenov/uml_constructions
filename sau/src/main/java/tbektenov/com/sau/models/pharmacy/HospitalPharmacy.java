package tbektenov.com.sau.models.pharmacy;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a hospital pharmacy associated with a specific hospital.
 */
@Data
@Entity
@Table(name = "HOSPITAL_PHARMACY")
@NoArgsConstructor
@AllArgsConstructor
public class HospitalPharmacy
        extends Pharmacy {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @OneToMany(mappedBy = "hospitalPharmacy", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderEntity> orders = new HashSet<>();

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
}
