package tbektenov.com.sau.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a hospital entity with related attributes and relationships.
 *
 * <p>This class uses JPA annotations to map to a database table and Lombok's {@code @Data}
 * annotation to automatically generate getters, setters, and other utility methods.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code id}: The unique identifier for the hospital.</li>
 *   <li>{@code name}: The name of the hospital (cannot be null or empty).</li>
 *   <li>{@code address}: The address of the hospital (cannot be null or empty).</li>
 *   <li>{@code laboratories}: The list of laboratories associated with the hospital.</li>
 *   <li>{@code doctors}: The set of doctors associated with the hospital.</li>
 * </ul>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>{@code laboratories}: One-to-many relationship with {@link Laboratory}, with cascade operations and orphan removal enabled.</li>
 * </ul>
 *
 * @see Laboratory
 * @see lombok.Data
 */
@Data
@Entity
@Table(name = "HOSPITAL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id", nullable = false)
    private Long id;

    @NotBlank(message = "Name cannot be null or empty")
    @Column(name = "name", nullable = false)
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
}
