package tbektenov.com.sau.models;

import jakarta.persistence.*;
import lombok.*;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "HOSPITALIZATION")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospitalization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospitalization_id")
    private Long id;

    @Column(name = "start_date", updatable = false, nullable = false)
    private LocalDate startDate = LocalDate.now();

    @Column(name = "endDate")
    private LocalDate endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_ward_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HospitalWard hospitalWard;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", updatable = false, nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StayingPatient patient;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "nurse_hospitalization",
            joinColumns = @JoinColumn(name = "hospitalization_id"),
            inverseJoinColumns = @JoinColumn(name = "nurse_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Nurse> nurses = new HashSet<>();
}
