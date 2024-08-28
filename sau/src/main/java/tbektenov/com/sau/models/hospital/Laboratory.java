package tbektenov.com.sau.models.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a laboratory entity associated with a hospital.
 *
 * <p>This entity manages the details of a hospital's laboratory, including its floor location
 * and its association with both the hospital and the doctors who work there.</p>
 */
@Data
@Entity
@Table(name = "LABORATORY")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laboratory_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "floor", nullable = false, updatable = false)
    private Integer floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Doctor> doctors = new HashSet<>();
}
