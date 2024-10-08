package tbektenov.com.sau.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.config.validator.DoctorAndPatientAreNotSame;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Patient;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an appointment between a doctor and a patient.
 *
 * <p>Mapped to the "APPOINTMENTS" table in the database.</p>
 *
 * <p>Includes details like date, status, doctor, and patient.</p>
 */
@Entity
@Data
@Table(name = "APPOINTMENTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DoctorAndPatientAreNotSame
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Appointment.details",
                attributeNodes = {
                        @NamedAttributeNode(value = "doctor", subgraph = "doctor.hospital")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "doctor.hospital",
                                attributeNodes = {
                                        @NamedAttributeNode("hospital")
                                }
                        )
                }
        )
)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", nullable = false, updatable = false)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LocalDate date = LocalDate.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @EqualsAndHashCode.Exclude
    private AppointmentStatus appointmentStatus = AppointmentStatus.UPCOMING;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Doctor doctor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;

    /**
     * Marks the given appointment as finished by setting its status to archived.
     *
     * @param appointment the appointment to be finished
     * @return the updated appointment with its status set to archived
     * @throws InvalidArgumentsException if the appointment is null
     */
    public static Appointment finishAppointment(Appointment appointment) {
        if (appointment != null) {
            appointment.setAppointmentStatus(AppointmentStatus.ARCHIVED);
            return appointment;
        } else {
            throw new InvalidArgumentsException("appointment is null.");
        }
    }
}
