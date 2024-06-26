package tbektenov.com.sau.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.CreateUpdateHospitalPharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.HospitalPharmacyDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.repositories.HospitalPharmacyRepo;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.services.IHospitalPharmacyService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing hospital pharmacy data.
 * This class provides services for creating, retrieving, updating, and deleting hospital pharmacies
 * linked to specific hospitals. It ensures that all operations are validated against the existence
 * of associated hospitals and implements business rules regarding the management of hospital pharmacy entities.
 *
 * @author Service
 * @see IHospitalPharmacyService
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
    public HospitalPharmacyServiceImpl(HospitalPharmacyRepo hospitalPharmacyRepo, HospitalRepo hospitalRepo) {
        this.hospitalPharmacyRepo = hospitalPharmacyRepo;
        this.hospitalRepo = hospitalRepo;
    }

    /** {@inheritDoc} */
    @Override
    public HospitalPharmacyDTO createHospitalPharmacy(Long hospitalId, CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO) {
        HospitalPharmacy hospitalPharmacy = mapToEntity(createUpdateHospitalPharmacyDTO);

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found")
        );

        hospitalPharmacy.setHospital(hospital);

        HospitalPharmacy newHospitalPharmacy = hospitalPharmacyRepo.save(hospitalPharmacy);

        return mapToDto(newHospitalPharmacy);
    }

    /** {@inheritDoc} */
    @Override
    public List<HospitalPharmacyDTO> getHospitalPharmaciesByHospitalId(Long hospitalId) {
        hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital was not found.")
        );

        List<HospitalPharmacy> hospitalPharmacies = hospitalPharmacyRepo.findByHospitalId(hospitalId);

        return hospitalPharmacies.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
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

    /** {@inheritDoc} */
    @Override
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

    /** {@inheritDoc} */
    @Override
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
     * Maps a HospitalPharmacy entity to a HospitalPharmacyDTO.
     *
     * @param hospitalPharmacy the hospital pharmacy entity to map
     * @return the mapped DTO
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
     * Maps a CreateUpdateHospitalPharmacyDTO to a HospitalPharmacy entity.
     *
     * @param createUpdateHospitalPharmacyDTO the DTO to map from
     * @return the mapped entity
     */
    private HospitalPharmacy mapToEntity(CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO) {
        HospitalPharmacy hospitalPharmacy = new HospitalPharmacy();
        hospitalPharmacy.setName(createUpdateHospitalPharmacyDTO.getName());
        hospitalPharmacy.setCompoundPharmacy(createUpdateHospitalPharmacyDTO.isCompoundPharmacy());
        return hospitalPharmacy;
    }
}
