package tbektenov.com.sau.models.user.patientRoles;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import tbektenov.com.sau.models.user.userRoles.Patient;

import java.time.LocalDate;

/**
 * Entity representing a patient who has left the hospital.
 */
@Data
@Entity
@Table(name = "LEFT_PATIENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeftPatient {
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "date_of_leave")
    private LocalDate dateOfLeave = LocalDate.now();

    @NotBlank(message = "Conclusion should contain something.")
    @Column(name = "conclusion", nullable = false, updatable = false)
    private String conclusion;
}
