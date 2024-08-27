package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.hospital.HospitalDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.CreateUpdatePrivatePharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyResponse;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.repositories.PrivatePharmacyRepo;
import tbektenov.com.sau.services.IPrivatePharmacyService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Private Pharmacy operations.
 */
@Service
public class PrivatePharmacyServiceImpl
    implements IPrivatePharmacyService {

    private PrivatePharmacyRepo privatePharmacyRepo;
    private HospitalRepo hospitalRepo;

    @Autowired
    public PrivatePharmacyServiceImpl(PrivatePharmacyRepo privatePharmacyRepo, HospitalRepo hospitalRepo) {
        this.privatePharmacyRepo = privatePharmacyRepo;
        this.hospitalRepo = hospitalRepo;
    }

    /**
     * Creates a new private pharmacy.
     *
     * @param createUpdatePrivatePharmacyDTO DTO containing details for creating a private pharmacy.
     * @return The created PrivatePharmacyDTO.
     * @throws InvalidArgumentsException if validation fails.
     */
    @Override
    @Transactional
    public PrivatePharmacyDTO createPrivatePharmacy(CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO) {
        validateCreateUpdatePrivatePharmacyDTO(createUpdatePrivatePharmacyDTO);

        PrivatePharmacy privatePharmacy = mapToEntity(createUpdatePrivatePharmacyDTO);

        PrivatePharmacy newPrivatePharmacy = privatePharmacyRepo.save(privatePharmacy);

        return mapToDto(newPrivatePharmacy);
    }

    /**
     * Retrieves all private pharmacies with pagination.
     *
     * @param pageNo   The page number to retrieve.
     * @param pageSize The number of records per page.
     * @return A response containing the paginated list of PrivatePharmacyDTOs.
     */
    @Override
    @Transactional
    public PrivatePharmacyResponse getAllPrivatePharmacies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<PrivatePharmacy> pharmacies = privatePharmacyRepo.findAll(pageable);
        List<PrivatePharmacy> listOfPharmacies = pharmacies.getContent();
        List<PrivatePharmacyDTO> content = listOfPharmacies.stream().map(pharmacy -> mapToDto(pharmacy)).toList();

        PrivatePharmacyResponse privatePharmacyResponse = new PrivatePharmacyResponse();
        privatePharmacyResponse.setContent(content);
        privatePharmacyResponse.setPageNo(pharmacies.getNumber());
        privatePharmacyResponse.setPageSize(pharmacies.getSize());
        privatePharmacyResponse.setTotalElements(pharmacies.getTotalElements());
        privatePharmacyResponse.setTotalPages(pharmacies.getTotalPages());
        privatePharmacyResponse.setLast(pharmacies.isLast());

        return privatePharmacyResponse;
    }

    /**
     * Retrieves a private pharmacy by its ID.
     *
     * @param privatePharmacyId The ID of the private pharmacy.
     * @return The PrivatePharmacyDTO with the specified ID.
     * @throws ObjectNotFoundException if the private pharmacy is not found.
     */
    @Override
    @Transactional
    public PrivatePharmacyDTO getPrivatePharmacyById(Long privatePharmacyId) {
        PrivatePharmacy privatePharmacy = privatePharmacyRepo.findById(privatePharmacyId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Private Pharmacy with id: %d was not found", privatePharmacyId))
        );

        return mapToDto(privatePharmacy);
    }

    /**
     * Updates an existing private pharmacy.
     *
     * @param createUpdatePrivatePharmacyDTO DTO containing updated details.
     * @param privatePharmacyId              The ID of the private pharmacy to update.
     * @return The updated PrivatePharmacyDTO.
     * @throws ObjectNotFoundException if the private pharmacy is not found.
     */
    @Override
    @Transactional
    public PrivatePharmacyDTO updatePrivatePharmacy(CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO, Long privatePharmacyId) {
        PrivatePharmacy privatePharmacy = privatePharmacyRepo.findById(privatePharmacyId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Private Pharmacy with id: %d was not found", privatePharmacyId))
        );

        if (createUpdatePrivatePharmacyDTO.getName() != null && !createUpdatePrivatePharmacyDTO.getName().isEmpty()) {
            privatePharmacy.setName(createUpdatePrivatePharmacyDTO.getName());
        }

        if (createUpdatePrivatePharmacyDTO.getAddress() != null && !createUpdatePrivatePharmacyDTO.getAddress().isEmpty()) {
            privatePharmacy.setAddress(createUpdatePrivatePharmacyDTO.getAddress());
        }

        if (!createUpdatePrivatePharmacyDTO.getPharmaCompany().isEmpty()) {
            privatePharmacy.setPharmaCompany(createUpdatePrivatePharmacyDTO.getPharmaCompany());
        }

        privatePharmacy.setCompoundPharmacy(createUpdatePrivatePharmacyDTO.isCompoundPharmacy());

        PrivatePharmacy updatedPrivatePharmacy = privatePharmacyRepo.save(privatePharmacy);

        return mapToDto(updatedPrivatePharmacy);
    }

    /**
     * Deletes a private pharmacy by its ID.
     *
     * @param privatePharmacyId The ID of the private pharmacy to delete.
     * @throws ObjectNotFoundException if the private pharmacy is not found.
     */
    @Override
    @Transactional
    public void deletePrivatePharmacy(Long privatePharmacyId) {
        PrivatePharmacy privatePharmacy = privatePharmacyRepo.findById(privatePharmacyId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Private Pharmacy with id: %d was not found", privatePharmacyId))
        );

        privatePharmacyRepo.delete(privatePharmacy);
    }

    /**
     * Adds a partner hospital to a private pharmacy.
     *
     * @param pharmacyId The ID of the private pharmacy.
     * @param hospitalId The ID of the hospital to add as a partner.
     * @return The updated PrivatePharmacyDTO.
     * @throws ObjectNotFoundException    if the pharmacy or hospital is not found.
     * @throws InvalidArgumentsException if the hospital is already a partner.
     */
    @Override
    @Transactional
    public PrivatePharmacyDTO addPartnerHospital(Long pharmacyId, Long hospitalId) {
        PrivatePharmacy pharmacy = privatePharmacyRepo.findById(pharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Private pharmacy not found.")
        );

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital not found.")
        );

        if (!pharmacy.getPartnerHospitals().contains(hospital)) {
            pharmacy.getPartnerHospitals().add(hospital);
            privatePharmacyRepo.save(pharmacy);
        } else {
            throw new InvalidArgumentsException("The hospital is already a partner.");
        }

        return mapToDto(pharmacy);
    }

    /**
     * Removes a partner hospital from a private pharmacy.
     *
     * @param pharmacyId The ID of the private pharmacy.
     * @param hospitalId The ID of the hospital to remove as a partner.
     * @return The updated PrivatePharmacyDTO.
     * @throws ObjectNotFoundException    if the pharmacy or hospital is not found.
     * @throws InvalidArgumentsException if the hospital is not a partner.
     */
    @Override
    @Transactional
    public PrivatePharmacyDTO removePartnerHospital(Long pharmacyId, Long hospitalId) {
        PrivatePharmacy pharmacy = privatePharmacyRepo.findById(pharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Private pharmacy not found.")
        );

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital not found.")
        );

        if (pharmacy.getPartnerHospitals().contains(hospital)) {
            pharmacy.getPartnerHospitals().remove(hospital);
            privatePharmacyRepo.save(pharmacy);
        } else {
            throw new InvalidArgumentsException("The hospital is not a partner.");
        }

        return mapToDto(pharmacy);
    }

    /**
     * Validates the details for creating or updating a private pharmacy.
     *
     * @param createUpdatePrivatePharmacyDTO The DTO to validate.
     * @throws InvalidArgumentsException if validation fails.
     */
    private void validateCreateUpdatePrivatePharmacyDTO(CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO) {
        if (createUpdatePrivatePharmacyDTO.getName() == null || createUpdatePrivatePharmacyDTO.getName().isEmpty()) {
            throw new InvalidArgumentsException("Name cannot be empty or null.");
        }

        if (createUpdatePrivatePharmacyDTO.getAddress() == null || createUpdatePrivatePharmacyDTO.getAddress().isEmpty()) {
            throw new InvalidArgumentsException("Address cannot be empty or null.");
        }

        if (createUpdatePrivatePharmacyDTO.getPharmaCompany().isEmpty()) {
            throw new InvalidArgumentsException("Pharma company cannot be empty string, but can be null.");
        }
    }

    /**
     * Converts a PrivatePharmacy entity to a PrivatePharmacyDTO.
     *
     * @param privatePharmacy The PrivatePharmacy entity.
     * @return The corresponding PrivatePharmacyDTO.
     */
    private PrivatePharmacyDTO mapToDto(PrivatePharmacy privatePharmacy) {
        PrivatePharmacyDTO privatePharmacyDTO = new PrivatePharmacyDTO();

        privatePharmacyDTO.setId(privatePharmacy.getId());
        privatePharmacyDTO.setName(privatePharmacy.getName());
        privatePharmacyDTO.setCompoundPharmacy(privatePharmacy.isCompoundPharmacy());
        privatePharmacyDTO.setAddress(privatePharmacy.getAddress());
        privatePharmacyDTO.setPharmaCompany(privatePharmacy.getPharmaCompany());
        privatePharmacyDTO.setPartnerHospitals(privatePharmacy.getPartnerHospitals().stream()
                .map(hospital -> {
                    HospitalDTO hospitalDTO = new HospitalDTO();
                    hospitalDTO.setHospitalId(hospital.getId());
                    hospitalDTO.setName(hospital.getName());
                    hospitalDTO.setAddress(hospital.getAddress());
                    return hospitalDTO;
                }).collect(Collectors.toList()));

        return privatePharmacyDTO;
    }

    /**
     * Converts a CreateUpdatePrivatePharmacyDTO to a PrivatePharmacy entity.
     *
     * @param createUpdatePrivatePharmacyDTO The DTO with private pharmacy details.
     * @return The mapped PrivatePharmacy entity.
     */
    private PrivatePharmacy mapToEntity(CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO) {
        PrivatePharmacy privatePharmacy = new PrivatePharmacy();

        privatePharmacy.setName(createUpdatePrivatePharmacyDTO.getName());
        privatePharmacy.setCompoundPharmacy(createUpdatePrivatePharmacyDTO.isCompoundPharmacy());
        privatePharmacy.setAddress(createUpdatePrivatePharmacyDTO.getAddress());
        privatePharmacy.setPharmaCompany(createUpdatePrivatePharmacyDTO.getPharmaCompany());

        return privatePharmacy;
    }
}
