package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.patientRoles.validator.OnePatientCheck;

import java.util.HashSet;
import java.util.Set;

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
                                        @NamedAttributeNode("treatmentTracker"),
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
public class Patient {
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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Appointment> appointments = new HashSet<>();

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StayingPatient stayingPatient;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LeftPatient leftPatient;

    /**
     * Adds an appointment to the patient's list of appointments.
     *
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        if (appointment != null && !appointments.contains(appointment)) {
            this.appointments.add(appointment);
        }
    }
}
