package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.repositories.DoctorRepo;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IDoctorService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing doctors.
 *
 * Provides methods to create, retrieve, update, and delete doctors,
 * and to manage their associations with hospitals.
 */
@Service
public class DoctorServiceImpl
        implements IDoctorService {

    private DoctorRepo doctorRepo;
    private UserRepo userRepo;

    @Autowired
    public DoctorServiceImpl(DoctorRepo doctorRepo,
                             UserRepo userRepo) {
        this.doctorRepo = doctorRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepo.findAll();
        return doctors.stream().map(doctor -> mapToDto(doctor)).collect(Collectors.toList());
    }

    /**
     * Maps a Doctor entity to a DoctorDTO.
     *
     * @param doctor The Doctor entity to map.
     * @return The mapped DoctorDTO with the doctor's details.
     */
    private DoctorDTO mapToDto(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getUser().getName());
        doctorDTO.setSurname(doctor.getUser().getSurname());
        doctorDTO.setSpecialization(doctor.getSpecialization());
        doctorDTO.setHospitalName(doctor.getHospital().getName());
        return doctorDTO;
    }
}
