package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.user.UserEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a nurse entity associated with a user and multiple hospitalizations.
 *
 * <p>This class manages the relationship between a nurse and hospitalizations,
 * and it is mapped to the database using JPA annotations.</p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Nurse.hospitalizations",
                attributeNodes = {
                        @NamedAttributeNode("hospitalizations")
                }
        )
)
public class Nurse{
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "nurse_hospitalization",
            joinColumns = @JoinColumn(name = "nurse_id"),
            inverseJoinColumns = @JoinColumn(name = "hospitalization_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Hospitalization> hospitalizations = new HashSet<>();

    /**
     * Adds a hospitalization to the nurse's list of hospitalizations.
     *
     * @param hospitalization the hospitalization to add
     */
    public void addHospitalization(Hospitalization hospitalization) {
        if (hospitalization != null && !hospitalizations.contains(hospitalization)) {
            hospitalizations.add(hospitalization);
            if (!hospitalization.getNurses().contains(this)) {
                hospitalization.addNurse(this);
            }
        }
    }
}
