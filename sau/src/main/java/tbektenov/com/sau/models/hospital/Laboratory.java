package tbektenov.com.sau.models.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import java.util.HashSet;
import java.util.Objects;
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

    @OneToMany(mappedBy = "laboratory", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Doctor> doctors = new HashSet<>();

    /**
     * Adds a doctor to this laboratory. Ensures that the doctor belongs to the same hospital
     * as the laboratory and handles the bidirectional relationship between the doctor and the laboratory.
     *
     * @param doctor the doctor to be added to this laboratory
     * @throws InvalidArgumentsException if the doctor is null, from a different hospital,
     *                                   already assigned to this laboratory, or assigned to another laboratory.
     */
    public void addDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new InvalidArgumentsException("Doctor cannot be null");
        }

        if (!Objects.equals(this.hospital.getId(), doctor.getHospital().getId())) {
            throw new InvalidArgumentsException("Doctor and lab are from different hospitals");
        }

        if (doctor.getLaboratory() != null && !doctor.getLaboratory().equals(this)) {
            doctor.getLaboratory().removeDoctor(doctor);
        }

        if (doctors.contains(doctor)) {
            throw new InvalidArgumentsException("Doctor already assigned to this laboratory");
        }

        doctors.add(doctor);
        if (doctor.getLaboratory() != this) {
            doctor.setLaboratory(this);
        }
    }

    /**
     * Removes a doctor from this laboratory. Ensures that the doctor belongs to the same hospital
     * as the laboratory and handles the bidirectional relationship between the doctor and the laboratory.
     *
     * @param doctor the doctor to be removed from this laboratory
     * @throws InvalidArgumentsException if the doctor is null, from a different hospital,
     *                                   or not assigned to this laboratory.
     */
    public void removeDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new InvalidArgumentsException("Doctor cannot be null");
        }

        if (!Objects.equals(this.hospital.getId(), doctor.getHospital().getId())) {
            throw new InvalidArgumentsException("Doctor and lab are from different hospital");
        }

        if (!doctors.contains(doctor)) {
            throw new InvalidArgumentsException("Doctor is not assigned");
        }

        doctors.remove(doctor);
        if (doctor.getLaboratory() == this) {
            doctor.setLaboratory(null);
        }
    }
}
