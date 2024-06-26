package tbektenov.com.sau.services.implementation;

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
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ILaboratoryService} interface that provides CRUD operations
 * for managing laboratories associated with hospitals.
 */
@Service
public class LaboratoryServiceImpl implements ILaboratoryService {

    private final LaboratoryRepo laboratoryRepo;
    private final HospitalRepo hospitalRepo;

    /**
     * Constructs a new LaboratoryServiceImp with the specified laboratory and hospital repositories.
     *
     * @param laboratoryRepo The repository for managing Laboratory entities.
     * @param hospitalRepo   The repository for managing Hospital entities.
     */
    @Autowired
    public LaboratoryServiceImpl(LaboratoryRepo laboratoryRepo, HospitalRepo hospitalRepo) {
        this.laboratoryRepo = laboratoryRepo;
        this.hospitalRepo = hospitalRepo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LaboratoryDTO createLaboratory(Long hospitalId, CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO) {
        Laboratory laboratory = mapToEntity(createUpdateLaboratoryDTO);

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found")
        );

        laboratory.setHospital(hospital);

        Laboratory newLaboratory = laboratoryRepo.save(laboratory);

        return mapToDto(newLaboratory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LaboratoryDTO> getLaboratoriesByHospitalId(Long hospitalId) {
        hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        List<Laboratory> laboratories = laboratoryRepo.findByHospitalId(hospitalId);

        return laboratories.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LaboratoryDTO getLaboratoryById(Long hospitalId, Long laboratoryId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        Laboratory laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow(
                () -> new ObjectNotFoundException("Laboratory was not found.")
        );

        if (laboratory.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This laboratory does not belong to this hospital.");
        }

        return mapToDto(laboratory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LaboratoryDTO updateLaboratory(Long hospitalId, Long laboratoryId, CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        Laboratory laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow(
                () -> new ObjectNotFoundException("Laboratory was not found.")
        );

        if (laboratory.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This laboratory does not belong to this hospital.");
        }

        laboratory.setFloor(createUpdateLaboratoryDTO.getFloor());

        Laboratory updatedLaboratory = laboratoryRepo.save(laboratory);

        return mapToDto(updatedLaboratory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLaboratory(Long hospitalId, Long laboratoryId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        Laboratory laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow(
                () -> new ObjectNotFoundException("Laboratory was not found.")
        );

        if (laboratory.getHospital().getId() != hospital.getId()) {
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
