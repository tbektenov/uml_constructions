package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.user.UserEntity;

import java.util.HashSet;
import java.util.Set;

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

    public void addHospitalization(Hospitalization hospitalization) {
        if (hospitalization != null && !hospitalizations.contains(hospitalization)) {
            hospitalizations.add(hospitalization);
            if (!hospitalization.getNurses().contains(this)) {
                hospitalization.addNurse(this);
            }
        }
    }
}
