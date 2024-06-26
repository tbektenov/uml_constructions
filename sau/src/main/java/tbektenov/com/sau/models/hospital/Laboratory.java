package tbektenov.com.sau.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a laboratory entity associated with a hospital.
 *
 * <p>This class uses JPA annotations to map to a database table and Lombok annotations
 * to automatically generate getters, setters, constructors, and other utility methods.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code id}: The unique identifier for the laboratory.</li>
 *   <li>{@code floor}: The floor on which the laboratory is located.</li>
 *   <li>{@code hospital}: The hospital to which this laboratory belongs (many-to-one relationship).</li>
 * </ul>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>{@code hospital}: Many-to-one relationship with {@link Hospital}, with lazy fetching and a join column named {@code hospital_id}.</li>
 * </ul>
 *
 * @see Hospital
 * @see lombok.Data
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
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
