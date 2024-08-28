package tbektenov.com.sau.services.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;
import tbektenov.com.sau.dtos.left_patient.ChangeToLeftPatientDTO;
import tbektenov.com.sau.dtos.patient.CreatePatientDTO;
import tbektenov.com.sau.dtos.patient.PatientDTO;
import tbektenov.com.sau.dtos.patient.UpdatePatientDTO;
import tbektenov.com.sau.dtos.staying_patient.ChangeToStayingPatientDTO;
import tbektenov.com.sau.dtos.user.UserDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.TreatmentTracker;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.repositories.*;
import tbektenov.com.sau.services.IPatientService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Patient operations.
 */
@Service
public class PatientServiceImpl
        implements IPatientService {
    private final NurseRepo nurseRepo;
    private final HospitalWardRepo hospitalWardRepo;
    private PatientRepo patientRepo;
    private UserRepo userRepo;
    private LeftPatientRepo leftPatientRepo;
    private StayingPatientRepo stayingPatientRepo;
    private EntityManager entityManager;

    @Autowired
    public PatientServiceImpl(PatientRepo patientRepo,
                              UserRepo userRepo,
                              LeftPatientRepo leftPatientRepo,
                              StayingPatientRepo stayingPatientRepo,
                              EntityManager entityManager,
                              NurseRepo nurseRepo,
                              HospitalWardRepo hospitalWardRepo) {
        this.patientRepo = patientRepo;
        this.userRepo = userRepo;
        this.leftPatientRepo = leftPatientRepo;
        this.stayingPatientRepo = stayingPatientRepo;
        this.entityManager = entityManager;
        this.nurseRepo = nurseRepo;
        this.hospitalWardRepo = hospitalWardRepo;
    }

    /**
     * Retrieves all patients.
     *
     * @return List of PatientDTOs.
     */
    @Override
    @Transactional
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepo.findAll();
        return patients.stream().map(patient -> mapToDto(patient)).collect(Collectors.toList());
    }

    /**
     * Creates a new patient.
     *
     * @param createPatientDTO DTO containing details to create a patient.
     * @return The created PatientDTO.
     * @throws InvalidArgumentsException if the user is already a patient.
     */
    @Override
    @Transactional
    public PatientDTO createPatient(CreatePatientDTO createPatientDTO) {
        UserEntity user = userRepo.findById(createPatientDTO.getUserId())
                .orElseThrow(() -> new ObjectNotFoundException("No user found."));

        Patient patient = new Patient();
        patient.setRhFactor(createPatientDTO.getRhFactor());
        patient.setBloodGroup(createPatientDTO.getBloodGroup());

        return mapToDto(patientRepo.save(patient));
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id The ID of the patient.
     * @return The PatientDTO with the specified ID.
     * @throws ObjectNotFoundException if no patient is found with the given ID.
     */
    @Override
    @Transactional
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No patient with id: %d was found.", id)
                )
        );
        return mapToDto(patient);
    }

    /**
     * Updates a patient's details.
     *
     * @param updatePatientDTO DTO containing updated details.
     * @param id               The ID of the patient to update.
     * @return The updated PatientDTO.
     * @throws ObjectNotFoundException if no patient is found with the given ID.
     */
    @Override
    @Transactional
    public PatientDTO updatePatient(UpdatePatientDTO updatePatientDTO, Long id) {
        Patient patient = patientRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );

        if (!updatePatientDTO.getRhFactor().toString().isEmpty()) {
            patient.setRhFactor(updatePatientDTO.getRhFactor());
        }

        if (!updatePatientDTO.getBloodGroup().toString().isEmpty()) {
            patient.setBloodGroup(updatePatientDTO.getBloodGroup());
        }

        return mapToDto(patientRepo.save(patient));
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id The ID of the patient to delete.
     * @throws ObjectNotFoundException if no patient is found with the given ID.
     */
    @Override
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );

        patientRepo.delete(patient);
    }

    /**
     * Adds a new appointment for a patient.
     *
     * @param createAppointmentDTO DTO containing details to create a new appointment.
     * @return The updated PatientDTO after adding the appointment.
     */
    @Override
    @Transactional
    public PatientDTO addAppointment(CreateAppointmentDTO createAppointmentDTO) {
        return null;
    }

    /**
     * Updates an existing appointment for a patient.
     *
     * @param createAppointmentDTO DTO containing updated appointment details.
     * @param id                   The ID of the patient whose appointment is to be updated.
     * @return The updated PatientDTO after modifying the appointment.
     */
    @Override
    @Transactional
    public PatientDTO updateAppointment(CreateAppointmentDTO createAppointmentDTO, Long id) {
        return null;
    }

    /**
     * Cancels an existing appointment for a patient.
     *
     * @param id The ID of the patient whose appointment is to be canceled.
     * @return The updated PatientDTO after canceling the appointment.
     */
    @Override
    @Transactional
    public PatientDTO cancelAppointment(Long id) {
        return null;
    }

    /**
     * Changes the status of a patient to "staying".
     *
     * @param patientId                 The ID of the patient to update.
     * @param changeToStayingPatientDTO DTO containing details for the transition to staying patient.
     * @return A confirmation message indicating the patient's status has been updated.
     * @throws ObjectNotFoundException if the patient, nurse, or ward cannot be found.
     */
    @Override
    @Transactional
    public String changeToStayingPatient(Long patientId, ChangeToStayingPatientDTO changeToStayingPatientDTO) {
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
        TreatmentTracker treatmentTracker = new TreatmentTracker();

        stayingPatient.setPatient(patient);

        stayingPatient.setHospitalization(hospitalization);

        treatmentTracker.setDate(LocalDate.now());
        treatmentTracker.setGotTreatmentToday(true);
        treatmentTracker.setPatient(stayingPatient);
        stayingPatient.setTreatmentTracker(treatmentTracker);

        patient.setStayingPatient(stayingPatient);

        stayingPatientRepo.save(stayingPatient);
        return "Patient is now staying";
    }

    /**
     * Changes the status of a patient to "left".
     *
     * @param patientId              The ID of the patient to update.
     * @param changeToLeftPatientDTO DTO containing details for the transition to left patient.
     * @return A confirmation message indicating the patient has left.
     * @throws ObjectNotFoundException if the patient cannot be found or if the conclusion is missing.
     */
    @Override
    @Transactional
    public String changeToLeftPatient(Long patientId, ChangeToLeftPatientDTO changeToLeftPatientDTO) {
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

        return "Patient has left";
    }


    /**
     * Converts a Patient entity to a PatientDTO.
     *
     * @param patient The Patient entity.
     * @return The corresponding PatientDTO.
     */
    private PatientDTO mapToDto(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setRhFactor(patient.getRhFactor());
        patientDTO.setBloodGroup(patient.getBloodGroup());
        patientDTO.setAppointments(patient.getAppointments().stream()
                .map(appointment -> {
                    AppointmentDTO appointmentDTO = new AppointmentDTO();
                    appointmentDTO.setId(appointment.getId());
                    appointmentDTO.setSpecialization(appointment.getDoctor().getSpecialization());
                    appointmentDTO.setHospital(appointment.getDoctor().getHospital().getName());
                    appointmentDTO.setHospitalAddress(appointment.getDoctor().getHospital().getAddress());
                    appointmentDTO.setDate(appointment.getDate());
                    appointmentDTO.setAppointmentStatus(appointment.getAppointmentStatus());
                    return appointmentDTO;
                }).collect(Collectors.toSet()));

        UserDTO userDTO = new UserDTO();

        patientDTO.setUser(userDTO);
        return patientDTO;
    }

    /**
     * Converts a CreatePatientDTO to a Patient entity.
     *
     * @param createPatientDTO The DTO with patient details.
     * @return The mapped Patient entity.
     */
    private Patient mapToEntity(CreatePatientDTO createPatientDTO) {
        Patient patient = new Patient();
        UserEntity user = userRepo.findById(createPatientDTO.getUserId()).orElseThrow(() -> new ObjectNotFoundException("No such user."));
        patient.setBloodGroup(createPatientDTO.getBloodGroup());
        patient.setRhFactor(createPatientDTO.getRhFactor());

        return patient;
    }
}
