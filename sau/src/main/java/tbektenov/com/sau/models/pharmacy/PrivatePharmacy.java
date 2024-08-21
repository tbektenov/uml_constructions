package tbektenov.com.sau.models.pharmacy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Private Pharmacy.
 */
@Data
@Entity
@Table(name = "PRIVATE_PHARMACY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"address", "company"})
})
@NoArgsConstructor
@AllArgsConstructor
public class PrivatePharmacy
        extends Pharmacy {

    @NotBlank(message = "address cannot be blank.")
    @Column(name = "Address", updatable = false, nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String address;

    @NotBlank(message = "company cannot be blank")
    @Column(name = "Company", updatable = false, nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String pharmaCompany;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pharmacy_hospital_partners",
            joinColumns = @JoinColumn(name = "pharmacy_id"),
            inverseJoinColumns = @JoinColumn(name = "hospital_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Hospital> partnerHospitals = new HashSet<>();

    @Builder
    public PrivatePharmacy(String name, boolean isCompoundPharmacy, String address, String pharmaCompany) {
        this.name = name;
        this.isCompoundPharmacy = isCompoundPharmacy;
        this.address = address;
        this.pharmaCompany = pharmaCompany;
    }

    /**
     * Checks if a given hospital is a partner.
     *
     * @param hospital the hospital to check
     * @return true if the hospital is a partner, false otherwise
     */
    public boolean checkIfHospitalIsPartner(Hospital hospital) {
        return partnerHospitals.contains(hospital);
    }

    public void addPartnerHospital(Hospital hospital) {
        if (hospital != null && !partnerHospitals.contains(hospital)) {
            this.partnerHospitals.add(hospital);
            if (!hospital.getPartnerPharmacies().contains(this)) {
                hospital.addPartnerPharmacy(this);
            }
        }
    }

}
