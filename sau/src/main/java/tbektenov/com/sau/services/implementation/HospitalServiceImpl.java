package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.dtos.hospital.*;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.repositories.DoctorRepo;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.repositories.PrivatePharmacyRepo;
import tbektenov.com.sau.services.IHospitalService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides services for managing hospitals.
 */
@Service
public class HospitalServiceImpl
        implements IHospitalService {

    private HospitalRepo hospitalRepo;
    private PrivatePharmacyRepo privatePharmacyRepo;
    private DoctorRepo doctorRepo;

    /**
     * Initializes the service with required repositories.
     *
     * @param hospitalRepo repository for hospital data
     * @param privatePharmacyRepo repository for private pharmacy data
     * @param doctorRepo repository for doctor data
     */
    @Autowired
    public HospitalServiceImpl(HospitalRepo hospitalRepo, PrivatePharmacyRepo privatePharmacyRepo, DoctorRepo doctorRepo) {
        this.hospitalRepo = hospitalRepo;
        this.privatePharmacyRepo = privatePharmacyRepo;
        this.doctorRepo = doctorRepo;
    }

    /**
     * Creates a new hospital.
     *
     * @param createUpdateHospitalDTO DTO with hospital details
     * @return the created HospitalDTO
     * @throws InvalidArgumentsException if the DTO contains invalid data
     */
    @Override
    @Transactional
    public HospitalDTO createHospital(CreateUpdateHospitalDTO createUpdateHospitalDTO) {
        validateCreateUpdateHospitalDTO(createUpdateHospitalDTO);

        Hospital hospital = mapToEntity(createUpdateHospitalDTO);

        Hospital newHospital = hospitalRepo.save(hospital);

        return mapToDto(newHospital);
    }

    /**
     * Retrieves a paginated list of all hospitals.
     *
     * @param pageNo the page number to retrieve
     * @param pageSize the number of hospitals per page
     * @return the paginated list of hospitals
     */
    @Override
    @Transactional
    public HospitalResponse getAllHospitals(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Hospital> hospitals = hospitalRepo.findAll(pageable);
        List<Hospital> listOfHospitals = hospitals.getContent();
        List<HospitalDTO> content = listOfHospitals.stream().map(hospital -> mapToDto(hospital)).collect(Collectors.toList());

        HospitalResponse hospitalResponse = new HospitalResponse();
        hospitalResponse.setContent(content);
        hospitalResponse.setPageNo(hospitals.getNumber());
        hospitalResponse.setPageSize(hospitals.getSize());
        hospitalResponse.setTotalElements(hospitals.getTotalElements());
        hospitalResponse.setTotalPages(hospitals.getTotalPages());
        hospitalResponse.setLast(hospitals.isLast());

        return hospitalResponse;
    }

    /**
     * Retrieves a hospital by its ID.
     *
     * @param id the ID of the hospital
     * @return the HospitalDTO
     * @throws ObjectNotFoundException if the hospital is not found
     */
    @Override
    @Transactional
    public HospitalDTO getHospitalById(Long id) {
        Hospital hospital = hospitalRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No hospital with id: %d was found.", id)
                )
        );
        return mapToDto(hospital);
    }

    /**
     * Updates an existing hospital.
     *
     * @param createUpdateHospitalDTO DTO with updated hospital details
     * @param id the ID of the hospital to update
     * @return the updated HospitalDTO
     * @throws ObjectNotFoundException if the hospital is not found
     */
    @Override
    @Transactional
    public HospitalDTO updateHospital(CreateUpdateHospitalDTO createUpdateHospitalDTO, Long id) {
        Hospital hospital = hospitalRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Hospital could not be updated because it was not found.")
        );

        // This method does not actually update the hospital's fields based on the DTO
        Hospital updatedHospital = hospitalRepo.save(hospital);
        return mapToDto(updatedHospital);
    }

    /**
     * Deletes a hospital by its ID.
     *
     * @param id the ID of the hospital to delete
     * @throws ObjectNotFoundException if the hospital is not found
     */
    @Override
    @Transactional
    public void deleteHospital(Long id) {
        Hospital hospital = hospitalRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Hospital could not be deleted.")
        );
        hospitalRepo.delete(hospital);
    }

    /**
     * Adds a private pharmacy as a partner to a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param pharmacyId the ID of the private pharmacy
     * @return the updated HospitalDTO
     * @throws ObjectNotFoundException if the hospital or pharmacy is not found
     * @throws InvalidArgumentsException if the pharmacy is already a partner
     */
    @Override
    @Transactional
    public HospitalDTO addPartnerPharmacy(Long hospitalId, Long pharmacyId) {
        PrivatePharmacy pharmacy = privatePharmacyRepo.findById(pharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Private pharmacy not found.")
        );

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital not found.")
        );

        if (!hospital.getPartnerPharmacies().contains(pharmacy)) {
            hospital.getPartnerPharmacies().add(pharmacy);
            hospitalRepo.save(hospital);
        } else {
            throw new InvalidArgumentsException("The hospital is already a partner.");
        }

        return mapToDto(hospital);
    }

    /**
     * Removes a private pharmacy from the list of hospital partners.
     *
     * @param hospitalId the ID of the hospital
     * @param pharmacyId the ID of the private pharmacy
     * @return the updated HospitalDTO
     * @throws ObjectNotFoundException if the hospital or pharmacy is not found
     * @throws InvalidArgumentsException if the pharmacy is not a partner
     */
    @Override
    @Transactional
    public HospitalDTO removePartnerPharmacy(Long hospitalId, Long pharmacyId) {
        PrivatePharmacy pharmacy = privatePharmacyRepo.findById(pharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Private pharmacy not found.")
        );

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital not found.")
        );

        if (hospital.getPartnerPharmacies().contains(pharmacy)) {
            hospital.getPartnerPharmacies().remove(pharmacy);
            hospitalRepo.save(hospital);
        } else {
            throw new InvalidArgumentsException("The hospital is not a partner.");
        }

        return mapToDto(hospital);
    }

    /**
     * Hires a doctor at a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param doctorId the ID of the doctor
     * @return the updated HospitalDTO
     * @throws ObjectNotFoundException if the hospital or doctor is not found
     * @throws InvalidArgumentsException if the doctor is already employed at the hospital
     */
    @Override
    @Transactional
    public HospitalDTO hireDoctor(Long hospitalId, Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(
                () -> new ObjectNotFoundException("Doctor not found.")
        );

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital not found.")
        );

        if (!hospital.getDoctors().contains(doctor)) {
            hospital.getDoctors().add(doctor);
            doctor.setHospital(hospital);
            hospitalRepo.save(hospital);
            doctorRepo.save(doctor);
        } else {
            throw new InvalidArgumentsException("Doctor already works there.");
        }

        return mapToDto(hospital);
    }

    /**
     * Fires a doctor from a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param doctorId the ID of the doctor
     * @return the updated HospitalDTO
     * @throws ObjectNotFoundException if the hospital or doctor is not found
     * @throws InvalidArgumentsException if the doctor is not employed at the hospital
     */
    @Override
    @Transactional
    public HospitalDTO fireDoctor(Long hospitalId, Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(
                () -> new ObjectNotFoundException("Doctor not found.")
        );

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital not found.")
        );

        if (hospital.getDoctors().contains(doctor)) {
            hospital.getDoctors().remove(doctor);
            doctor.setHospital(null);
            hospitalRepo.save(hospital);
            doctorRepo.save(doctor);
        } else {
            throw new InvalidArgumentsException("This doctor does work there.");
        }

        return mapToDto(hospital);
    }

    /**
     * Validates the CreateUpdateHospitalDTO.
     * Ensures the name and address are not null or empty.
     *
     * @param createUpdateHospitalDTO the DTO to be validated
     * @throws InvalidArgumentsException if validation fails
     */
    private void validateCreateUpdateHospitalDTO(CreateUpdateHospitalDTO createUpdateHospitalDTO) {
        if (createUpdateHospitalDTO.getName() == null || createUpdateHospitalDTO.getName().isEmpty()) {
            throw new InvalidArgumentsException("Name cannot be empty or null.");
        }

        if (createUpdateHospitalDTO.getAddress() == null || createUpdateHospitalDTO.getAddress().isEmpty()) {
            throw new InvalidArgumentsException("Address cannot be empty or null.");
        }
    }

    /**
     * Maps a Hospital entity to a HospitalDTO.
     *
     * @param hospital the Hospital entity
     * @return the corresponding HospitalDTO
     */
    private HospitalDTO mapToDto(Hospital hospital) {
        HospitalDTO hospitalDTO = new HospitalDTO();
        hospitalDTO.setHospitalId(hospital.getId());
        hospitalDTO.setName(hospital.getName());
        hospitalDTO.setAddress(hospital.getAddress());
        hospitalDTO.setDoctors(hospital.getDoctors().stream()
                .map(doctor -> {
                    DoctorDTO doctorDTO = new DoctorDTO();
                    doctorDTO.setId(doctor.getId());
                    doctorDTO.setName(doctor.getUser().getName());
                    doctorDTO.setSurname(doctor.getUser().getSurname());
                    doctorDTO.setSpecialization(doctor.getSpecialization());
                    doctorDTO.setHospitalName(hospital.getName());
                    return doctorDTO;
                }).collect(Collectors.toSet()));

        return hospitalDTO;
    }

    /**
     * Maps a CreateUpdateHospitalDTO to a Hospital entity.
     *
     * @param hospitalDTO the CreateUpdateHospitalDTO
     * @return the corresponding Hospital entity
     */
    private Hospital mapToEntity(CreateUpdateHospitalDTO hospitalDTO) {
        Hospital hospital = new Hospital();
        hospital.setName(hospitalDTO.getName());
        hospital.setAddress(hospitalDTO.getAddress());
        return hospital;
    }
}
