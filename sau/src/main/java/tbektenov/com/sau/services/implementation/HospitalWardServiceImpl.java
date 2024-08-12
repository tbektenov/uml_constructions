package tbektenov.com.sau.services.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.hospitalWard.CreateUpdateHospitalWardDTO;
import tbektenov.com.sau.dtos.hospitalWard.HospitalWardDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.repositories.HospitalWardRepo;
import tbektenov.com.sau.services.IHospitalWardService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing hospital wards.
 *
 * Provides methods to create, retrieve, update, and delete hospital wards.
 */
@Service
public class HospitalWardServiceImpl
    implements IHospitalWardService {

    private HospitalWardRepo hospitalWardRepo;
    private HospitalRepo hospitalRepo;
    private EntityManager entityManager;

    @Autowired
    public HospitalWardServiceImpl(HospitalWardRepo hospitalWardRepo,
                                   HospitalRepo hospitalRepo,
                                   EntityManager entityManager) {
        this.hospitalWardRepo = hospitalWardRepo;
        this.hospitalRepo = hospitalRepo;
        this.entityManager = entityManager;
    }

    /**
     * Creates a new hospital ward and associates it with a hospital.
     *
     * @param hospitalId ID of the hospital to associate with.
     * @param createUpdateHospitalWardDTO DTO containing the ward details.
     * @return The created HospitalWardDTO.
     */
    @Override
    public HospitalWardDTO createHospitalWard(Long hospitalId, CreateUpdateHospitalWardDTO createUpdateHospitalWardDTO) {
        if (createUpdateHospitalWardDTO.getCapacity() < 1) {
            throw new InvalidArgumentsException("Capacity cannot be less than 1.");
        }

        try {
            Long count = (Long) entityManager.createNativeQuery(
                        "select count(*) " +
                            "from hospital_ward hw " +
                            "where hw.hospital_id = :hospitalId and hw.ward_num = :wardNum"
                    )
                    .setParameter("hospitalId", hospitalId)
                    .setParameter("wardNum", createUpdateHospitalWardDTO.getWardNum())
                    .getSingleResult();

            if (count > 0) {
                throw new InvalidArgumentsException("Hospital already has a ward with ward number: " + createUpdateHospitalWardDTO.getWardNum());
            }

            Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                    () -> new ObjectNotFoundException("Hospital was not found.")
            );

            HospitalWard hospitalWard = mapToEntity(createUpdateHospitalWardDTO);
            hospitalWard.setHospital(hospital);

            return mapToDto(hospitalWardRepo.save(hospitalWard));

        } catch (NoResultException e) {
            throw new ObjectNotFoundException("Hospital or Ward number was not found.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating the hospital ward.", e);
        }
    }

    /**
     * Retrieves a list of wards associated with a hospital.
     *
     * @param hospitalId ID of the hospital.
     * @return A list of HospitalWardDTOs.
     */
    @Override
    public List<HospitalWardDTO> getHospitalWardsByHospitalId(Long hospitalId) {
        hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        List<HospitalWard> hospitalWards = hospitalWardRepo.findByHospitalId(hospitalId);

        return hospitalWards.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a specific ward by its ID and the associated hospital ID.
     *
     * @param hospitalId ID of the hospital.
     * @param hospitalWardId ID of the ward.
     * @return The HospitalWardDTO with the ward's details.
     */
    @Override
    public HospitalWardDTO getHospitalWardById(Long hospitalId, Long hospitalWardId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        HospitalWard hospitalWard = hospitalWardRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital ward was not found.")
        );

        if (hospitalWard.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This hospital ward does not belong to this hospital.");
        }

        return mapToDto(hospitalWard);
    }

    /**
     * Updates the details of a specific hospital ward.
     *
     * @param hospitalId ID of the hospital.
     * @param hospitalWardId ID of the ward.
     * @param createUpdateHospitalWardDTO DTO containing the updated ward details.
     * @return The updated HospitalWardDTO.
     */
    @Override
    public HospitalWardDTO updateHospitalWard(Long hospitalId, Long hospitalWardId, CreateUpdateHospitalWardDTO createUpdateHospitalWardDTO) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        HospitalWard hospitalWard = hospitalWardRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital ward was not found.")
        );

        if (hospitalWard.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This hospital ward does not belong to this hospital.");
        }

        if (createUpdateHospitalWardDTO.getWardNum() != null && !createUpdateHospitalWardDTO.getWardNum().isEmpty()) {
            hospitalWard.setWardNum(createUpdateHospitalWardDTO.getWardNum());
        }

        if (createUpdateHospitalWardDTO.getCapacity() > 0) {
            hospitalWard.setCapacity(createUpdateHospitalWardDTO.getCapacity());
        }

        HospitalWard updatedHospitalWard = hospitalWardRepo.save(hospitalWard);

        return mapToDto(updatedHospitalWard);
    }

    /**
     * Deletes a hospital ward by its ID and the associated hospital ID.
     *
     * @param hospitalId ID of the hospital.
     * @param hospitalWardId ID of the ward.
     */
    @Override
    public void deleteHospitalWard(Long hospitalId, Long hospitalWardId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        HospitalWard hospitalWard = hospitalWardRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital ward was not found.")
        );

        if (hospitalWard.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This hospital ward does not belong to this hospital.");
        }

        hospitalWardRepo.delete(hospitalWard);
    }

    /**
     * Maps a HospitalWard entity to a HospitalWardDTO.
     *
     * @param hospitalWard The HospitalWard entity to map.
     * @return The mapped HospitalWardDTO.
     */
    private HospitalWardDTO mapToDto(HospitalWard hospitalWard) {
        HospitalWardDTO hospitalWardDTO = new HospitalWardDTO();

        hospitalWardDTO.setId(hospitalWard.getId());
        hospitalWardDTO.setWardNum(hospitalWard.getWardNum());
        hospitalWardDTO.setCapacity(hospitalWard.getCapacity());

        return hospitalWardDTO;
    }

    /**
     * Maps a CreateUpdateHospitalWardDTO to a HospitalWard entity.
     *
     * @param createUpdateHospitalWardDTO The DTO with ward details.
     * @return The mapped HospitalWard entity.
     */
    private HospitalWard mapToEntity(CreateUpdateHospitalWardDTO createUpdateHospitalWardDTO) {
        HospitalWard hospitalWard = new HospitalWard();

        hospitalWard.setWardNum(createUpdateHospitalWardDTO.getWardNum());
        hospitalWard.setCapacity(createUpdateHospitalWardDTO.getCapacity());

        return hospitalWard;
    }
}
