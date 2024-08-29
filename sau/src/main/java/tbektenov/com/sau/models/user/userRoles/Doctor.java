package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.user.UserEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a doctor entity associated with a user, hospital, and optionally a laboratory.
 *
 * <p>This class handles the relationship of a doctor with appointments, orders, and hospitalizations.
 * It is mapped to the database using JPA annotations.</p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Doctor.detailsHospitalAndLaboratory",
                attributeNodes = {
                        @NamedAttributeNode("hospital"),
                        @NamedAttributeNode("laboratory"),
                        @NamedAttributeNode(value = "user", subgraph = "user.subgraph")
                },
                subgraphs = {
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
})
public class Doctor{

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @NotNull(message = "Doctor must have specialization.")
    @Column(name = "specialization")
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratory_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Laboratory laboratory;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderEntity> orders = new HashSet<>();

    /**
     * Assigns a nurse to a specific hospitalization.
     *
     * @param nurse the nurse to be assigned
     * @param hospitalization the hospitalization to which the nurse is assigned
     */
    public void assignNurseToHospitalization(Nurse nurse,
                                             Hospitalization hospitalization) {
        if (nurse != null && hospitalization != null) {

            if (Objects.equals(nurse.getId(), hospitalization.getPatient().getId())) {
                throw new InvalidArgumentsException("Nurse and patient are the same person.");
            }

            if (nurse.getHospitalizations().contains(hospitalization)) {
                throw new InvalidArgumentsException("Nurse is already assigned to this hospitalization.");
            }

            nurse.addHospitalization(hospitalization);
            if (!nurse.getHospitalizations().contains(hospitalization)) {
                hospitalization.addNurse(nurse);
            }
        }
    }

    /**
     * Adds an appointment to the doctor's list of appointments.
     *
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    /**
     * Sets the doctor's laboratory. Removes the doctor from the current lab if different.
     * Throws an exception if the lab is from a different hospital.
     *
     * @param laboratory The new lab to associate with. If null, clears the current lab.
     * @throws InvalidArgumentsException if the lab is from a different hospital.
     */

    public void setLaboratory(Laboratory laboratory) {
        if (this.laboratory != null && !this.laboratory.equals(laboratory)) {
            if (this.laboratory.getDoctors().contains(this)) {
                this.laboratory.removeDoctor(this);
            }
        }

        if (laboratory == null) {
            this.laboratory = null;
            return;
        }

        if (!Objects.equals(this.hospital.getId(), laboratory.getHospital().getId())) {
            throw new InvalidArgumentsException("Doctor and lab are from different hospitals");
        }

        this.laboratory = laboratory;
        if (!laboratory.getDoctors().contains(this)) {
            laboratory.addDoctor(this);
        }
    }
}
