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

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@OnePatientCheck
public class Patient{
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "patient_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Column(name = "surname", nullable = false, updatable = false)
    private String surname;

    @NotBlank(message = "SSN cannot be null or empty.")
    @Pattern(regexp = "^\\d{9}$\n")
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
    List<Appointment> appointments = new ArrayList<>();

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
}
