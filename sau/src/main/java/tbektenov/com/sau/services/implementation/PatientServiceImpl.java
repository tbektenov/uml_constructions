package tbektenov.com.sau.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;
import tbektenov.com.sau.dtos.patient.CreatePatientDTO;
import tbektenov.com.sau.dtos.patient.PatientDTO;
import tbektenov.com.sau.dtos.patient.UpdatePatientDTO;
import tbektenov.com.sau.dtos.user.UserDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.PatientRepo;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IPatientService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Patient operations.
 */
@Service
public class PatientServiceImpl
    implements IPatientService
{
    private PatientRepo patientRepo;
    private UserRepo userRepo;

    @Autowired
    public PatientServiceImpl(PatientRepo patientRepo, UserRepo userRepo) {
        this.patientRepo = patientRepo;
        this.userRepo = userRepo;
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
        UserEntity user = userRepo.findById(createPatientDTO.getUserId()).orElseThrow(() -> new ObjectNotFoundException("No user found."));

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
    public PatientDTO deleteAppointment(Long id) {
        return null;
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
