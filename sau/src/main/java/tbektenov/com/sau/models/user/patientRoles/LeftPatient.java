package tbektenov.com.sau.models.user.patientRoles;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.format.annotation.DateTimeFormat;
import tbektenov.com.sau.models.user.userRoles.Patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

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
    private String conclusion;
}
