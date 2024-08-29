package tbektenov.com.sau.models.user.patientRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.TreatmentTracker;
import tbektenov.com.sau.models.user.userRoles.Patient;

/**
 * Entity representing a hospitalized patient.
 */
@Data
@Entity
@Table(name = "STAYING_PATIENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StayingPatient {
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;

    @NotNull(message = "Hospitalization cannot be null.")
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospitalization hospitalization;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TreatmentTracker treatmentTracker;
}
