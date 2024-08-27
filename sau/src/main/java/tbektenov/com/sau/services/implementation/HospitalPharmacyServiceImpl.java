package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.CreateUpdateHospitalPharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.HospitalPharmacyDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.repositories.HospitalPharmacyRepo;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.services.IHospitalPharmacyService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing hospital pharmacies.
 * Provides methods to create, retrieve, update, and delete hospital pharmacies.
 */
@Service
public class HospitalPharmacyServiceImpl
    implements IHospitalPharmacyService{

    private HospitalPharmacyRepo hospitalPharmacyRepo;
    private HospitalRepo hospitalRepo;

    /**
     * Constructs a new HospitalPharmacyServiceImpl with necessary repository dependencies.
     *
     * @param hospitalPharmacyRepo the repository for hospital pharmacy data access
     * @param hospitalRepo the repository for hospital data access
     */
    @Autowired
    public HospitalPharmacyServiceImpl(HospitalPharmacyRepo hospitalPharmacyRepo,
                                       HospitalRepo hospitalRepo) {
        this.hospitalPharmacyRepo = hospitalPharmacyRepo;
        this.hospitalRepo = hospitalRepo;
    }

    /**
     * Creates a new hospital pharmacy for a specific hospital.
     *
     * @param hospitalId ID of the hospital
     * @param createUpdateHospitalPharmacyDTO DTO containing the pharmacy details
     * @return the created HospitalPharmacyDTO
     * @throws InvalidArgumentsException if the hospital already has a pharmacy with the same name
     * @throws ObjectNotFoundException if the hospital is not found
     */
    @Override
    @Transactional
    public HospitalPharmacyDTO createHospitalPharmacy(Long hospitalId, CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO) {
        if (hospitalPharmacyRepo.existsByHospitalIdAndName(hospitalId, createUpdateHospitalPharmacyDTO.getName())) {
            throw new InvalidArgumentsException("Hospital already has such pharmacy.");
        }

        HospitalPharmacy hospitalPharmacy = mapToEntity(createUpdateHospitalPharmacyDTO);

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found")
        );

        hospitalPharmacy.setHospital(hospital);

        HospitalPharmacy newHospitalPharmacy = hospitalPharmacyRepo.save(hospitalPharmacy);

        return mapToDto(newHospitalPharmacy);
    }

    /**
     * Retrieves all pharmacies for a specific hospital.
     *
     * @param hospitalId ID of the hospital
     * @return a list of HospitalPharmacyDTOs
     * @throws ObjectNotFoundException if the hospital is not found
     */
    @Override
    @Transactional
    public List<HospitalPharmacyDTO> getHospitalPharmaciesByHospitalId(Long hospitalId) {
        hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        List<HospitalPharmacy> hospitalPharmacies = hospitalPharmacyRepo.findByHospitalId(hospitalId);

        return hospitalPharmacies.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a specific hospital pharmacy by its ID.
     *
     * @param hospitalId ID of the hospital
     * @param hospitalPharmacyId ID of the pharmacy
     * @return the HospitalPharmacyDTO
     * @throws ObjectNotFoundException if the hospital or pharmacy is not found or if the pharmacy does not belong to the hospital
     */
    @Override
    @Transactional
    public HospitalPharmacyDTO getHospitalPharmacyById(Long hospitalId, Long hospitalPharmacyId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        HospitalPharmacy hospitalPharmacy = hospitalPharmacyRepo.findById(hospitalPharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital Pharmacy was not found.")
        );

        if (hospitalPharmacy.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This pharmacy does not belong to this hospital.");
        }

        return mapToDto(hospitalPharmacy);
    }

    /**
     * Updates an existing hospital pharmacy.
     *
     * @param hospitalId ID of the hospital
     * @param hospitalPharmacyId ID of the pharmacy
     * @param createUpdateHospitalPharmacyDTO DTO with updated details
     * @return the updated HospitalPharmacyDTO
     * @throws ObjectNotFoundException if the hospital or pharmacy is not found or if the pharmacy does not belong to the hospital
     */
    @Override
    @Transactional
    public HospitalPharmacyDTO updateHospitalPharmacy(Long hospitalId, Long hospitalPharmacyId, CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        HospitalPharmacy hospitalPharmacy = hospitalPharmacyRepo.findById(hospitalPharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital Pharmacy was not found.")
        );

        if (hospitalPharmacy.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This pharmacy does not belong to this hospital.");
        }

        hospitalPharmacy.setName(createUpdateHospitalPharmacyDTO.getName());
        hospitalPharmacy.setCompoundPharmacy(createUpdateHospitalPharmacyDTO.isCompoundPharmacy());

        HospitalPharmacy updatedHospitalPharmacy = hospitalPharmacyRepo.save(hospitalPharmacy);

        return mapToDto(updatedHospitalPharmacy);
    }

    /**
     * Deletes a hospital pharmacy.
     *
     * @param hospitalId ID of the hospital
     * @param hospitalPharmacyId ID of the pharmacy
     * @throws ObjectNotFoundException if the hospital or pharmacy is not found or if the pharmacy does not belong to the hospital
     */
    @Override
    @Transactional
    public void deleteHospitalPharmacy(Long hospitalId, Long hospitalPharmacyId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        HospitalPharmacy hospitalPharmacy = hospitalPharmacyRepo.findById(hospitalPharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital Pharmacy was not found.")
        );

        if (hospitalPharmacy.getHospital().getId() != hospital.getId()) {
            throw new ObjectNotFoundException("This pharmacy does not belong to this hospital.");
        }

        hospitalPharmacyRepo.delete(hospitalPharmacy);
    }

    /**
     * Converts a HospitalPharmacy entity to a DTO.
     *
     * @param hospitalPharmacy the entity to convert
     * @return the corresponding DTO
     */
    private HospitalPharmacyDTO mapToDto(HospitalPharmacy hospitalPharmacy) {
        HospitalPharmacyDTO hospitalPharmacyDTO = new HospitalPharmacyDTO();
        hospitalPharmacyDTO.setId(hospitalPharmacy.getId());
        hospitalPharmacyDTO.setName(hospitalPharmacy.getName());
        hospitalPharmacyDTO.setCompoundPharmacy(hospitalPharmacy.isCompoundPharmacy());
        hospitalPharmacyDTO.setAddress(hospitalPharmacy.getHospital().getAddress());
        return hospitalPharmacyDTO;
    }

    /**
     * Converts a DTO to a HospitalPharmacy entity.
     *
     * @param createUpdateHospitalPharmacyDTO the DTO to convert
     * @return the corresponding entity
     */
    private HospitalPharmacy mapToEntity(CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO) {
        HospitalPharmacy hospitalPharmacy = new HospitalPharmacy();
        hospitalPharmacy.setName(createUpdateHospitalPharmacyDTO.getName());
        hospitalPharmacy.setCompoundPharmacy(createUpdateHospitalPharmacyDTO.isCompoundPharmacy());
        return hospitalPharmacy;
    }
}
