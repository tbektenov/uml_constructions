package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.patientRoles.validator.OnePatientCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient entity with related attributes and relationships.
 *
 * <p>This class manages the relationships between a patient and their appointments,
 * as well as their status as a staying or left patient.</p>
 */
@Entity
@Data
@Table(name = "PATIENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@OnePatientCheck
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Patient.details",
                attributeNodes = {
                        @NamedAttributeNode("appointments"),
                        @NamedAttributeNode("leftPatient"),
                        @NamedAttributeNode(value = "stayingPatient", subgraph = "sp.details")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "sp.details",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "hospitalization", subgraph = "nurses")
                                }
                        ),
                        @NamedSubgraph(
                                name = "nurses",
                                attributeNodes = {
                                        @NamedAttributeNode("nurses")
                                }
                        )
                })
)
public class Patient
    implements IPatient{
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @NotBlank(message = "SSN cannot be null or empty.")
    @Pattern(regexp = "^\\d{9}$")
    @Column(name = "ssn", nullable = false, updatable = false, unique = true)
    private String ssn;

    @Enumerated(EnumType.STRING)
    @Column(name = "rh_factor")
    private RhFactor rhFactor;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Appointment> appointments = new ArrayList<>();

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StayingPatient stayingPatient;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LeftPatient leftPatient;

    @Override
    public void cancelAppointmentForPatient(Appointment appointment) {
        if (appointment != null && this.appointments.contains(appointment)) {
            appointments.remove(appointment);
        } else {
            throw new InvalidArgumentsException("appointment is not assigned to this doctor.");
        }
    }
}
