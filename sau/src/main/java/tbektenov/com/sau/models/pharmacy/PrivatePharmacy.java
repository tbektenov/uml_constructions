package tbektenov.com.sau.models.pharmacy;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Private Pharmacy.
 */
@Data
@Entity
@Table(name = "PRIVATE_PHARMACY")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivatePharmacy
        extends Pharmacy {

    private String address;

    private String pharmaCompany;

    /**
     * -- GETTER --
     *  Gets the list of partner hospitals.
     *
     * @return list of partner hospitals
     */
    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pharmacy_hospital_partners",
            joinColumns = @JoinColumn(name = "pharmacy_id"),
            inverseJoinColumns = @JoinColumn(name = "hospital_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Hospital> partnerHospitals = new HashSet<>();

    /**
     * Checks if a given hospital is a partner.
     *
     * @param hospital the hospital to check
     * @return true if the hospital is a partner, false otherwise
     */
    public boolean checkIfHospitalIsPartner(Hospital hospital) {
        return partnerHospitals.contains(hospital);
    }

}
