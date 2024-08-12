package tbektenov.com.sau.models.user.patientRoles;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.TreatmentTracker;
import tbektenov.com.sau.models.user.userRoles.Patient;

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

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospitalization hospitalization;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TreatmentTracker treatmentTracker;

}
