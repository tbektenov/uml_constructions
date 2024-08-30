package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;

import java.util.HashSet;
import java.util.Objects;
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
public class Nurse
    implements INurse{
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
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

            if (Objects.equals(hospitalization.getPatient().getId(), this.user.getId())) {
                throw new InvalidArgumentsException("Nurse and patient are the same person.");
            }

            hospitalizations.add(hospitalization);
            if (!hospitalization.getNurses().contains(this)) {
                hospitalization.addNurse(this);
            }
        }
    }

    /**
     * Removes the given hospitalization from this nurse's list.
     * Also removes the nurse from the hospitalization's list of nurses.
     *
     * @param hospitalization The hospitalization to remove. If null or not found, nothing happens.
     */
    public void removeHospitalization(Hospitalization hospitalization) {
        if (hospitalization != null && hospitalizations.contains(hospitalization)) {
            hospitalizations.remove(hospitalization);
            if (hospitalization.getNurses().contains(this)) {
                hospitalization.removeNurse(this);
            }
        }
    }

    /**
     * Returns a set of patients assigned to this nurse through their hospitalizations.
     *
     * @return A set of assigned patients.
     */
    public Set<UserEntity> getAssignedPatients() {
        Set<UserEntity> assignedPatients = new HashSet<>();
        for (Hospitalization hospitalization : hospitalizations) {
            StayingPatient stayingPatient = hospitalization.getPatient();
            assignedPatients.add(stayingPatient.getPatient().getUser());
        }
        return assignedPatients;
    }

    /**
     * Returns a set of hospital wards assigned to this nurse through their hospitalizations.
     *
     * @return A set of assigned hospital wards.
     */
    public Set<HospitalWard> getAssignedWards() {
        Set<HospitalWard> hospitalWards = new HashSet<>();
        for (Hospitalization hospitalization : hospitalizations) {
            HospitalWard hospitalWard = hospitalization.getHospitalWard();
            hospitalWards.add(hospitalWard);
        }
        return hospitalWards;
    }
}
