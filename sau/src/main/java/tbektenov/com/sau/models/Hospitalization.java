package tbektenov.com.sau.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tbektenov.com.sau.models.config.validator.AtLeastOneNurse;
import tbektenov.com.sau.models.config.validator.NurseAndPatientAreNotSame;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.userRoles.Nurse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a hospitalization event for a patient within a hospital ward.
 */
@Data
@Entity
@Table(name = "HOSPITALIZATION")
@NoArgsConstructor
@AtLeastOneNurse
@NurseAndPatientAreNotSame
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Hospitalization.details",
                attributeNodes = {
                        @NamedAttributeNode("nurses"),
                        @NamedAttributeNode("patient")
                }
        )
)
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
    @JoinColumn(name = "patient_id", updatable = false, nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StayingPatient patient;

    @ManyToMany(mappedBy = "hospitalizations", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Nurse> nurses = new HashSet<>();

    /**
     * Constructs a new Hospitalization with the given ward, patient, and nurse.
     *
     * @param hospitalWard the hospital ward where the patient is staying
     * @param patient      the patient being hospitalized
     * @param nurses        the nurse(s) assigned to the patient
     */
    @Builder
    public Hospitalization(
            HospitalWard hospitalWard,
            StayingPatient patient,
            Set<Nurse> nurses
    ) {
        if (nurses == null) {
            throw new IllegalArgumentException("At least one nurse must be assigned.");
        }

        this.startDate = LocalDate.now();
        this.hospitalWard = hospitalWard;
        hospitalWard.addHospitalization(this);
        this.patient = patient;
        patient.setHospitalization(this);
        this.nurses = nurses;
        nurses.forEach(nurse -> nurse.addHospitalization(this));
    }

    /**
     * Adds a nurse to the hospitalization.
     *
     * @param nurse The nurse to add.
     */
    public void addNurse(Nurse nurse) {
        if (nurse != null && !nurses.contains(nurse)) {
            nurses.add(nurse);
            if (!nurse.getHospitalizations().contains(this)) {
                nurse.addHospitalization(this);
            }
        }
    }

    public void removeNurse(Nurse nurse) {
        if (nurse != null && nurses.contains(nurse)) {
            this.nurses.remove(nurse);
            if (nurse.getHospitalizations().contains(this)) {
                nurse.removeHospitalization(this);
            }
        }
    }
}
