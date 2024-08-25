package tbektenov.com.sau.services.implementation;

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
 * Implementation of the {@link IHospitalService} interface.
 * Provides the business logic for managing hospitals.
 */
@Service
public class HospitalServiceImpl
    implements IHospitalService {

    private HospitalRepo hospitalRepo;
    private PrivatePharmacyRepo privatePharmacyRepo;
    private DoctorRepo doctorRepo;

    /**
     * Constructs a new {@code HospitalServiceImpl} with the specified hospital repository.
     *
     * @param hospitalRepo the repository to be used for CRUD operations on hospitals
     */
    @Autowired
    public HospitalServiceImpl(HospitalRepo hospitalRepo, PrivatePharmacyRepo privatePharmacyRepo, DoctorRepo doctorRepo) {
        this.hospitalRepo = hospitalRepo;
        this.privatePharmacyRepo = privatePharmacyRepo;
        this.doctorRepo = doctorRepo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HospitalDTO createHospital(CreateUpdateHospitalDTO createUpdateHospitalDTO) {
        validateCreateUpdateHospitalDTO(createUpdateHospitalDTO);

        Hospital hospital = mapToEntity(createUpdateHospitalDTO);

        Hospital newHospital = hospitalRepo.save(hospital);

        return mapToDto(newHospital);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    @Override
    public List<HospitalAndDoctorsDTO> getAllHospitalsWithDoctors() {
        List<Hospital> hospitals = hospitalRepo.findAll();
        return hospitals.stream().map(hospital -> mapToDtoWithDoctors(hospital)).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HospitalDTO getHospitalById(Long id) {
        Hospital hospital = hospitalRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No hospital with id: %d was found.", id)
                )
        );
        return mapToDto(hospital);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HospitalDTO updateHospital(CreateUpdateHospitalDTO createUpdateHospitalDTO, Long id) {
        Hospital hospital = hospitalRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Hospital could not be updated because it was not found.")
        );



        Hospital updatedHospital = hospitalRepo.save(hospital);
        return mapToDto(updatedHospital);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteHospital(Long id) {
        Hospital hospital = hospitalRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Hospital could not be deleted.")
        );
        hospitalRepo.delete(hospital);
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
     * Validates the given {@link CreateUpdateHospitalDTO}.
     * Throws an exception if the name or address is null or empty.
     *
     * @param createUpdateHospitalDTO the DTO to be validated
     * @throws InvalidArgumentsException if the name or address is null or empty
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
     * Maps a {@link Hospital} entity to a {@link HospitalDTO}.
     *
     * @param hospital the hospital entity to be mapped
     * @return the resulting hospital DTO
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

    private HospitalAndDoctorsDTO mapToDtoWithDoctors(Hospital hospital) {
        HospitalAndDoctorsDTO hospitalDTO = new HospitalAndDoctorsDTO();
        hospitalDTO.setId(hospital.getId());
        hospitalDTO.setDoctors(hospital.getDoctors().stream()
                .map(doctor -> {
                    DoctorDTO doctorDTO = new DoctorDTO();
                    doctorDTO.setId(doctor.getId());
                    doctorDTO.setSpecialization(doctor.getSpecialization());
                    return doctorDTO;
                }).collect(Collectors.toList()));

        return hospitalDTO;
    }

    /**
     * Maps a {@link CreateUpdateHospitalDTO} to a {@link Hospital} entity.
     *
     * @param hospitalDTO the DTO to be mapped
     * @return the resulting hospital entity
     */
    private Hospital mapToEntity(CreateUpdateHospitalDTO hospitalDTO) {
        Hospital hospital = new Hospital();
        hospital.setName(hospitalDTO.getName());
        hospital.setAddress(hospitalDTO.getAddress());
        return hospital;
    }
}
