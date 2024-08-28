package tbektenov.com.sau.models.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a hospital entity with related attributes and relationships.
 *
 * <p>This class maps to the "HOSPITAL" table in the database and includes relationships
 * with laboratories, hospital pharmacies, hospital wards, partner pharmacies, and doctors.</p>
 *
 * <p>The entity graph defined by {@code @NamedEntityGraph} allows for optimized fetching
 * of related entities by specifying subgraphs for complex queries.</p>
 */
@Data
@Entity
@Table(name = "HOSPITAL")
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Hospital.withPharmaciesAndDoctors",
                attributeNodes = {
                        @NamedAttributeNode("partnerPharmacies"),
                        @NamedAttributeNode(value = "doctors", subgraph = "doctor.subgraph")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "doctor.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "user", subgraph = "user.subgraph")
                                }
                        ),
                        @NamedSubgraph(
                                name = "user.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode("doctor"),
                                        @NamedAttributeNode(value = "patient", subgraph = "patient.subgraph"),
                                        @NamedAttributeNode("nurse")
                                }
                        ),
                        @NamedSubgraph(
                                name = "patient.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "stayingPatient", subgraph = "stayingPatient.subgraph"),
                                        @NamedAttributeNode("leftPatient")
                                }
                        ),
                        @NamedSubgraph(
                                name = "stayingPatient.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode("hospitalization"),
                                        @NamedAttributeNode("treatmentTracker")
                                }
                        )
                }
        )
)
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id", nullable = false)
    private Long id;

    @NotBlank(message = "Name cannot be null or empty")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Address cannot be null or empty")
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Laboratory> laboratories = new HashSet<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<HospitalPharmacy> hospitalPharmacies = new HashSet<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<HospitalWard> hospitalWards = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "pharmacy_hospital_partners",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<PrivatePharmacy> partnerPharmacies = new HashSet<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Doctor> doctors = new HashSet<>();

    /**
     * Adds a partner pharmacy to the hospital.
     * Ensures bidirectional consistency by adding the hospital to the pharmacy's partner hospitals.
     *
     * @param pharmacy the pharmacy to add as a partner
     */
    public void addPartnerPharmacy(PrivatePharmacy pharmacy) {
        if (pharmacy != null && !this.partnerPharmacies.contains(pharmacy)) {
            this.partnerPharmacies.add(pharmacy);
            if (!pharmacy.getPartnerHospitals().contains(this)) {
                pharmacy.addPartnerHospital(this);
            }
        }
    }

    public void removePartnerPharmacy(PrivatePharmacy pharmacy) {
        if (pharmacy != null && this.partnerPharmacies.contains(pharmacy)) {
            this.partnerPharmacies.remove(pharmacy);
            if (pharmacy.getPartnerHospitals().contains(this)) {
                pharmacy.removePartnerHospital(this);
            }
        }
    }
}
