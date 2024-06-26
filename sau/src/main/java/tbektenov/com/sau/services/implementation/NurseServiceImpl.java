package tbektenov.com.sau.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.nurse.CreateNurseDTO;
import tbektenov.com.sau.dtos.nurse.NurseDTO;
import tbektenov.com.sau.dtos.nurse.UpdateNurseDTO;
import tbektenov.com.sau.dtos.patient.CreatePatientDTO;
import tbektenov.com.sau.dtos.user.UserDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.NurseRepo;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.INurseService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of INurseService for managing nurse operations.
 */
@Service
public class NurseServiceImpl
    implements INurseService {

    private NurseRepo nurseRepo;
    private UserRepo userRepo;

    @Autowired
    public NurseServiceImpl(NurseRepo nurseRepo, UserRepo userRepo) {
        this.nurseRepo = nurseRepo;
        this.userRepo = userRepo;
    }

    /**
     * Retrieves all nurses.
     *
     * @return List of NurseDTOs.
     */
    @Override
    public List<NurseDTO> getAllNurses() {
        List<Nurse> nurses = nurseRepo.findAll();
        return nurses.stream().map(nurse -> mapToDto(nurse)).collect(Collectors.toList());
    }

    /**
     * Creates a new nurse.
     *
     * @param createNurseDTO DTO containing details to create a nurse.
     * @return The created NurseDTO.
     */
    @Override
    public NurseDTO createNurse(CreateNurseDTO createNurseDTO) {
        UserEntity user = userRepo.findById(createNurseDTO.getUserId()).orElseThrow(() -> new ObjectNotFoundException("No user found."));

        Nurse nurse = new Nurse();

        return mapToDto(nurseRepo.save(nurse));
    }

    /**
     * Retrieves a nurse by ID.
     *
     * @param id The ID of the nurse.
     * @return The NurseDTO with the specified ID.
     */
    @Override
    public NurseDTO getNurseById(Long id) {
        Nurse nurse = nurseRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No patient with id: %d was found.", id)
                )
        );
        return mapToDto(nurse);
    }

    /**
     * Updates a nurse's details.
     *
     * @param updateNurseDTO DTO containing updated details.
     * @param id             The ID of the nurse to update.
     * @return The updated NurseDTO.
     */
    @Override
    public NurseDTO updateNurse(UpdateNurseDTO updateNurseDTO, Long id) {
        Nurse nurse = nurseRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );

        return mapToDto(nurseRepo.save(nurse));
    }

    /**
     * Deletes a nurse by ID.
     *
     * @param id The ID of the nurse to delete.
     */
    @Override
    public void deleteNurse(Long id) {
        Nurse nurse = nurseRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );

        nurseRepo.delete(nurse);
    }

    /**
     * Maps a Nurse entity to a NurseDTO.
     *
     * @param nurse The Nurse entity.
     * @return The corresponding NurseDTO.
     */
    private NurseDTO mapToDto(Nurse nurse) {
        NurseDTO nurseDTO = new NurseDTO();
        nurseDTO.setId(nurseDTO.getId());

        UserDTO userDTO = new UserDTO();

        nurseDTO.setUserDTO(userDTO);
        return nurseDTO;
    }

    /**
     * Maps a CreatePatientDTO to a Patient entity.
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
