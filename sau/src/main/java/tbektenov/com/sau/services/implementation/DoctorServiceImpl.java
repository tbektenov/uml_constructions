package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.doctor.CreateDoctorDTO;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.dtos.doctor.DoctorResponse;
import tbektenov.com.sau.dtos.doctor.UpdateDoctorDTO;
import tbektenov.com.sau.dtos.order.CreateOrderDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.AppointmentStatus;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.OrderStatus;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.*;
import tbektenov.com.sau.services.IDoctorService;

import java.time.LocalDateTime;
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

    /**
     * Maps a CreateDoctorDTO to a Doctor entity.
     *
     * @param createDoctorDTO The data transfer object containing doctor details.
     * @return The mapped Doctor entity with the details from the DTO.
     */
    private Doctor mapToEntity(CreateDoctorDTO createDoctorDTO) {
        Doctor doctor = new Doctor();
        UserEntity user = userRepo.findById(createDoctorDTO.getUserId()).orElseThrow(() -> new ObjectNotFoundException("No such user."));
        doctor.setSpecialization(createDoctorDTO.getSpecialization());

        return doctor;
    }
}
