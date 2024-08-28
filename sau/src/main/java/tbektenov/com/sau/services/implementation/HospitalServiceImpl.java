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
