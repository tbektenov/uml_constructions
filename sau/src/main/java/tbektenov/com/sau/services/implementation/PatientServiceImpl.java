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
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.LeftPatientRepo;
import tbektenov.com.sau.repositories.PatientRepo;
import tbektenov.com.sau.repositories.StayingPatientRepo;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IPatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Patient operations.
 */
@Service
public class PatientServiceImpl
        implements IPatientService {
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
                              EntityManager entityManager) {
        this.patientRepo = patientRepo;
        this.userRepo = userRepo;
        this.leftPatientRepo = leftPatientRepo;
        this.stayingPatientRepo = stayingPatientRepo;
        this.entityManager = entityManager;
    }

    /**
     * Retrieves all patients.
     *
     * @return List of PatientDTOs.
     */
    @Override
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
    public void deletePatient(Long id) {
        Patient patient = patientRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );

        patientRepo.delete(patient);
    }

    @Override
    public PatientDTO addAppointment(CreateAppointmentDTO createAppointmentDTO) {
        return null;
    }

    @Override
    public PatientDTO updateAppointment(CreateAppointmentDTO createAppointmentDTO, Long id) {
        return null;
    }

    @Override
    public PatientDTO cancelAppointment(Long id) {
        return null;
    }

    @Override
    @Transactional
    public String changeToStayingPatient(Long patientId, ChangeToStayingPatientDTO changeToStayingPatientDTO) {
        try {
            Patient patient = patientRepo.findById(patientId)
                    .orElseThrow(() -> new ObjectNotFoundException("Patient not found."));

            if (patient.getLeftPatient() != null) {
                patient.setLeftPatient(null);
            }

            Object[] result = (Object[]) entityManager.createNativeQuery(
                            "select p.patient_id, hw.hospital_ward_id " +
                                    "from Patient p, Hospital_ward hw " +
                                    "where p.patient_id = :patientId " +
                                    "and hw.ward_num = :wardNum " +
                                    "and hw.hospital_id = :hospitalId"
                    )
                    .setParameter("patientId", patientId)
                    .setParameter("wardNum", changeToStayingPatientDTO.getWardNum())
                    .setParameter("hospitalId", changeToStayingPatientDTO.getHospitalId())
                    .getSingleResult();

            StayingPatient stayingPatient = new StayingPatient();
            stayingPatient.setPatient(patient);

            patient.setStayingPatient(stayingPatient);

            stayingPatientRepo.save(stayingPatient);

            return "Patient is now staying";
        } catch (NoResultException e) {
            throw new ObjectNotFoundException("No patient, ward, or hospital was found.");
        }
    }

    @Override
    @Transactional
    public String changeToLeftPatient(Long patientId, ChangeToLeftPatientDTO changeToLeftPatientDTO) {
        if (changeToLeftPatientDTO.getConclusion() == null || changeToLeftPatientDTO.getConclusion().isEmpty()) {
            throw new ObjectNotFoundException("Conclusion cannot be null or empty.");
        }
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (patient.getStayingPatient() != null) {
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
                    appointmentDTO.setPatient_id(appointment.getPatient().getId());
                    appointmentDTO.setDoctor_id(appointment.getDoctor().getId());
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
