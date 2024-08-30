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
                name = "Hospital.details",
                attributeNodes = {
                        @NamedAttributeNode("partnerPharmacies"),
                        @NamedAttributeNode(value = "doctors", subgraph = "doctor.subgraph"),
                        @NamedAttributeNode("laboratories")
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
                                        @NamedAttributeNode("hospitalization")
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

    @ManyToMany(mappedBy = "partnerHospitals", fetch = FetchType.LAZY)
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

    /**
     * Removes the given pharmacy from the list of partner pharmacies.
     * Also removes this hospital from the pharmacy's list of partner hospitals.
     *
     * @param pharmacy The pharmacy to remove. If null or not a partner, nothing happens.
     */
    public void removePartnerPharmacy(PrivatePharmacy pharmacy) {
        if (pharmacy != null && this.partnerPharmacies.contains(pharmacy)) {
            this.partnerPharmacies.remove(pharmacy);
            if (pharmacy.getPartnerHospitals().contains(this)) {
                pharmacy.removePartnerHospital(this);
            }
        }
    }

    /**
     * Adds a doctor to the hospital's list of doctors if not already present
     * and sets the hospital for the doctor.
     *
     * @param doctor the doctor to be added
     */
    public void addDoctor(Doctor doctor) {
        if (doctor != null && !this.doctors.contains(doctor)) {
            this.doctors.add(doctor);
            if (doctor.getHospital() != this) {
                doctor.setHospital(this);
            }
        }
    }

    /**
     * Creates a new laboratory associated with this hospital on the specified floor.
     *
     * @param floor the floor number where the laboratory will be located
     */
    public void createLaboratory(Integer floor) {
        if (floor != null) {
            Laboratory newLab = Laboratory.builder()
                    .floor(floor)
                    .hospital(this)
                    .build();

            this.laboratories.add(newLab);
        }
    }

    /**
     * Removes a laboratory from the hospital's list of laboratories.
     * Also disassociates the laboratory from the hospital.
     *
     * @param lab The laboratory to be removed.
     */
    public void removeLaboratory(Laboratory lab) {
        if (lab != null && this.laboratories.contains(lab)) {
            this.laboratories.remove(lab);
            if (lab.getHospital() != null) {
                lab.setHospital(null);
            }
        }
    }

    /**
     * Creates a new hospital ward associated with this hospital and adds it to the list of hospital wards.
     *
     * @param wardNum  the ward number or name
     * @param capacity the capacity of the ward
     */
    public void createHospitalWard(String wardNum, Integer capacity) {
        if (wardNum != null && !wardNum.isEmpty() && capacity != null) {
            HospitalWard newWard = HospitalWard.builder()
                    .wardNum(wardNum)
                    .capacity(capacity)
                    .hospital(this)
                    .build();

            this.hospitalWards.add(newWard);
        }
    }

    /**
     * Removes a hospital ward from the hospital's list of wards.
     * Also disassociates the ward from the hospital.
     *
     * @param ward The hospital ward to be removed.
     */
    public void removeWard(HospitalWard ward) {
        if (ward != null && this.hospitalWards.contains(ward)) {
            this.hospitalWards.remove(ward);
            if (ward.getHospital() != null) {
                ward.setHospital(null);
            }
        }
    }
}
