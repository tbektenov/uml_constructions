package tbektenov.com.sau.models.hospital;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tbektenov.com.sau.models.Hospitalization;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents a hospital ward within a hospital.
 *
 * <p>This entity is used to store details about a hospital ward, such as its number, capacity,
 * and the associated hospital. It also manages the hospitalizations that occur within the ward.</p>
 */
@Data
@Entity
@Table(name = "HOSPITAL_WARD")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalWard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_ward_id", nullable = false)
    private Long id;

    @NotBlank(message = "Ward number cannot be null or empty")
    @Column(name = "ward_num", nullable = false)
    private String wardNum;

    @NotNull
    @Min(value = 1, message = "Capacity of ward should be at least 1.")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @OneToMany(mappedBy = "hospitalWard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Hospitalization> hospitalizations = new HashSet<>();

    /**
     * Adds a hospitalization to the ward and ensures that the ward is correctly set in the hospitalization.
     *
     * @param hospitalization the hospitalization to add
     */
    public void addHospitalization(Hospitalization hospitalization) {
        if (hospitalization != null) {
            hospitalizations.add(hospitalization);
            if (!hospitalization.getHospitalWard().equals(this)) {
                hospitalization.setHospitalWard(this);
            }
        }
    }
}
