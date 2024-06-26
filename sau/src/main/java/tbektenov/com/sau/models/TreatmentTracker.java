package tbektenov.com.sau.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "TREATMENT_TRACKER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", updatable = false, nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StayingPatient patient;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "gotTreatmentToday", nullable = false, updatable = false)
    private Boolean gotTreatmentToday;
}
