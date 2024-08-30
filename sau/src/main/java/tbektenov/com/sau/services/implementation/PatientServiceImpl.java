package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.left_patient.ChangeToLeftPatientDTO;
import tbektenov.com.sau.dtos.staying_patient.ChangeToStayingPatientDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.repositories.*;
import tbektenov.com.sau.services.IPatientService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Service implementation for managing Patient operations.
 */
@Service
public class PatientServiceImpl
        implements IPatientService {
    private final NurseRepo nurseRepo;
    private final HospitalWardRepo hospitalWardRepo;
    private PatientRepo patientRepo;
    private LeftPatientRepo leftPatientRepo;
    private StayingPatientRepo stayingPatientRepo;

    @Autowired
    public PatientServiceImpl(PatientRepo patientRepo,
                              LeftPatientRepo leftPatientRepo,
                              StayingPatientRepo stayingPatientRepo,
                              NurseRepo nurseRepo,
                              HospitalWardRepo hospitalWardRepo) {
        this.patientRepo = patientRepo;
        this.leftPatientRepo = leftPatientRepo;
        this.stayingPatientRepo = stayingPatientRepo;
        this.nurseRepo = nurseRepo;
        this.hospitalWardRepo = hospitalWardRepo;
    }

    /**
     * Changes the status of a patient to "staying".
     *
     * @param patientId                 The ID of the patient to update.
     * @param changeToStayingPatientDTO DTO containing details for the transition to staying patient.
     * @throws ObjectNotFoundException if the patient, nurse, or ward cannot be found.
     */
    @Override
    @Transactional
    public void changeToStayingPatient(Long patientId, ChangeToStayingPatientDTO changeToStayingPatientDTO) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new ObjectNotFoundException("Patient not found."));

        Nurse nurse = nurseRepo.findById(changeToStayingPatientDTO.getNurseId())
                .orElseThrow(() -> new ObjectNotFoundException("Nurse not found."));

        HospitalWard hospitalWard = hospitalWardRepo.findByWardNumAndHospitalId(
                changeToStayingPatientDTO.getWardNum(), changeToStayingPatientDTO.getHospitalId()
        ).orElseThrow(() -> new ObjectNotFoundException("No such ward."));

        if (patient.getLeftPatient() != null) {
            leftPatientRepo.delete(patient.getLeftPatient());
            patient.setLeftPatient(null);
        }

        Set<Nurse> nurseSet = new HashSet<>();
        nurseSet.add(nurse);

        StayingPatient stayingPatient = new StayingPatient();
        Hospitalization hospitalization = Hospitalization.builder()
                .patient(stayingPatient)
                .nurses(nurseSet)
                .hospitalWard(hospitalWard)
                .build();

        stayingPatient.setPatient(patient);

        stayingPatient.setHospitalization(hospitalization);

        patient.setStayingPatient(stayingPatient);

        stayingPatientRepo.save(stayingPatient);
    }

    /**
     * Changes the status of a patient to "left".
     *
     * @param patientId              The ID of the patient to update.
     * @param changeToLeftPatientDTO DTO containing details for the transition to left patient.
     * @throws ObjectNotFoundException if the patient cannot be found or if the conclusion is missing.
     */
    @Override
    @Transactional
    public void changeToLeftPatient(Long patientId, ChangeToLeftPatientDTO changeToLeftPatientDTO) {
        if (changeToLeftPatientDTO.getConclusion() == null || changeToLeftPatientDTO.getConclusion().isEmpty()) {
            throw new ObjectNotFoundException("Conclusion cannot be null or empty.");
        }
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Hospitalization hospitalization = patient.getStayingPatient().getHospitalization();
        Set<Nurse> nurseSet = hospitalization.getNurses();

        nurseSet.forEach(nurse -> nurse.removeHospitalization(hospitalization));

        if (patient.getStayingPatient() != null) {
            stayingPatientRepo.delete(patient.getStayingPatient());
            patient.setStayingPatient(null);
        }

        LeftPatient leftPatient = new LeftPatient();
        leftPatient.setPatient(patient);
        leftPatient.setDateOfLeave(LocalDate.now());
        leftPatient.setConclusion(changeToLeftPatientDTO.getConclusion());

        patient.setLeftPatient(leftPatient);

        leftPatientRepo.save(leftPatient);

    }
}
