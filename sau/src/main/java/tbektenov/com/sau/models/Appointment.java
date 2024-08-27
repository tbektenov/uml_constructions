package tbektenov.com.sau.models;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Patient;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

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
    private LocalDate date = LocalDate.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
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
}
