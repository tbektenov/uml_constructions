package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.laboratory.CreateUpdateLaboratoryDTO;
import tbektenov.com.sau.dtos.laboratory.LaboratoryDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.repositories.LaboratoryRepo;
import tbektenov.com.sau.services.ILaboratoryService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation for managing laboratories.
 */
@Service
public class LaboratoryServiceImpl
        implements ILaboratoryService {

    private final LaboratoryRepo laboratoryRepo;
    private final HospitalRepo hospitalRepo;

    /**
     * Constructs a new LaboratoryServiceImpl with the specified laboratory and hospital repositories.
     *
     * @param laboratoryRepo The repository for managing Laboratory entities.
     * @param hospitalRepo   The repository for managing Hospital entities.
     */
    @Autowired
    public LaboratoryServiceImpl(LaboratoryRepo laboratoryRepo,
                                 HospitalRepo hospitalRepo) {
        this.laboratoryRepo = laboratoryRepo;
        this.hospitalRepo = hospitalRepo;
    }

    /**
     * Creates a new laboratory and associates it with a specified hospital.
     *
     * @param hospitalId ID of the hospital to associate with.
     * @param createUpdateLaboratoryDTO DTO containing the laboratory details.
     * @return The created LaboratoryDTO.
     * @throws ObjectNotFoundException if a laboratory with the same floor already exists in the hospital,
     *                                  or if the hospital is not found.
     */
    @Override
    @Transactional
    public LaboratoryDTO createLaboratory(Long hospitalId, CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO) {
        if (laboratoryRepo.existsByHospitalIdAndFloor(hospitalId, createUpdateLaboratoryDTO.getFloor())) {
            throw new ObjectNotFoundException("Laboratory already exists");
        }

        Laboratory laboratory = mapToEntity(createUpdateLaboratoryDTO);

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found")
        );

        laboratory.setHospital(hospital);

        Laboratory newLaboratory = laboratoryRepo.save(laboratory);

        return mapToDto(newLaboratory);
    }

    /**
     * Retrieves a list of laboratories associated with a specified hospital.
     *
     * @param hospitalId ID of the hospital.
     * @return A list of LaboratoryDTOs.
     * @throws ObjectNotFoundException if the hospital is not found.
     */
    @Override
    @Transactional
    public List<LaboratoryDTO> getLaboratoriesByHospitalId(Long hospitalId) {
        hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        List<Laboratory> laboratories = laboratoryRepo.findByHospitalId(hospitalId);

        return laboratories.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a specific laboratory by its ID and the associated hospital ID.
     *
     * @param hospitalId ID of the hospital.
     * @param laboratoryId ID of the laboratory.
     * @return The LaboratoryDTO with the laboratory's details.
     * @throws ObjectNotFoundException if the hospital or laboratory is not found,
     *                                  or if the laboratory does not belong to the specified hospital.
     */
    @Override
    @Transactional
    public LaboratoryDTO getLaboratoryById(Long hospitalId, Long laboratoryId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        Laboratory laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow(
                () -> new ObjectNotFoundException("Laboratory was not found.")
        );

        if (!Objects.equals(laboratory.getHospital().getId(), hospital.getId())) {
            throw new ObjectNotFoundException("This laboratory does not belong to this hospital.");
        }

        return mapToDto(laboratory);
    }

    /**
     * Updates the details of a specific laboratory.
     *
     * @param hospitalId ID of the hospital.
     * @param laboratoryId ID of the laboratory.
     * @param createUpdateLaboratoryDTO DTO containing the updated laboratory details.
     * @return The updated LaboratoryDTO.
     * @throws ObjectNotFoundException if the hospital or laboratory is not found,
     *                                  or if the laboratory does not belong to the specified hospital.
     */
    @Override
    @Transactional
    public LaboratoryDTO updateLaboratory(Long hospitalId, Long laboratoryId, CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        Laboratory laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow(
                () -> new ObjectNotFoundException("Laboratory was not found.")
        );

        if (!Objects.equals(laboratory.getHospital().getId(), hospital.getId())) {
            throw new ObjectNotFoundException("This laboratory does not belong to this hospital.");
        }

        laboratory.setFloor(createUpdateLaboratoryDTO.getFloor());

        Laboratory updatedLaboratory = laboratoryRepo.save(laboratory);

        return mapToDto(updatedLaboratory);
    }

    /**
     * Deletes a laboratory by its ID and the associated hospital ID.
     *
     * @param hospitalId ID of the hospital.
     * @param laboratoryId ID of the laboratory.
     * @throws ObjectNotFoundException if the hospital or laboratory is not found,
     *                                  or if the laboratory does not belong to the specified hospital.
     */
    @Override
    @Transactional
    public void deleteLaboratory(Long hospitalId, Long laboratoryId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        Laboratory laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow(
                () -> new ObjectNotFoundException("Laboratory was not found.")
        );

        if (!Objects.equals(laboratory.getHospital().getId(), hospital.getId())) {
            throw new ObjectNotFoundException("This laboratory does not belong to this hospital.");
        }

        laboratoryRepo.delete(laboratory);
    }

    /**
     * Maps a Laboratory entity to a LaboratoryDTO.
     *
     * @param laboratory The Laboratory entity to map.
     * @return The corresponding LaboratoryDTO.
     */
    private LaboratoryDTO mapToDto(Laboratory laboratory) {
        LaboratoryDTO laboratoryDTO = new LaboratoryDTO();
        laboratoryDTO.setId(laboratory.getId());
        laboratoryDTO.setFloor(laboratory.getFloor());
        return laboratoryDTO;
    }

    /**
     * Maps a CreateUpdateLaboratoryDTO to a Laboratory entity.
     *
     * @param createUpdateLaboratoryDTO The CreateUpdateLaboratoryDTO to map.
     * @return The corresponding Laboratory entity.
     */
    private Laboratory mapToEntity(CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO) {
        Laboratory laboratory = new Laboratory();
        laboratory.setFloor(createUpdateLaboratoryDTO.getFloor());
        return laboratory;
    }
}
