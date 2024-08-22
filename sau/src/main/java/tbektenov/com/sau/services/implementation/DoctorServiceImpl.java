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
import tbektenov.com.sau.dtos.hospital.HospitalDoctorCompDTO;
import tbektenov.com.sau.dtos.order.CreateOrderDTO;
import tbektenov.com.sau.dtos.user.UserDTO;
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
    private HospitalRepo hospitalRepo;
    private AppointmentRepo appointmentRepo;
    private HospitalPharmacyRepo hospitalPharmacyRepo;
    private OrderRepo orderRepo;

    @Autowired
    public DoctorServiceImpl(DoctorRepo doctorRepo,
                             UserRepo userRepo,
                             HospitalRepo hospitalRepo,
                             AppointmentRepo appointmentRepo,
                             HospitalPharmacyRepo hospitalPharmacyRepo,
                             OrderRepo orderRepo) {
        this.doctorRepo = doctorRepo;
        this.userRepo = userRepo;
        this.hospitalRepo = hospitalRepo;
        this.appointmentRepo = appointmentRepo;
        this.hospitalPharmacyRepo = hospitalPharmacyRepo;
        this.orderRepo = orderRepo;
    }

    /**
     * Retrieves a paginated list of all doctors.
     *
     * @param pageNo The page number to retrieve.
     * @param pageSize The number of doctors per page.
     * @return A DoctorResponse containing the paginated list of doctors.
     */
    @Override
    public DoctorResponse getAllDoctors(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Doctor> doctors = doctorRepo.findAll(pageable);
        List<Doctor> listOfDoctors = doctors.getContent();
        List<DoctorDTO> content = listOfDoctors.stream().map(doctor -> mapToDto(doctor)).collect(Collectors.toList());

        DoctorResponse doctorResponse = new DoctorResponse();
        doctorResponse.setContent(content);
        doctorResponse.setPageNo(doctors.getNumber());
        doctorResponse.setPageSize(doctors.getSize());
        doctorResponse.setTotalElements(doctors.getTotalElements());
        doctorResponse.setTotalPages(doctors.getTotalPages());
        doctorResponse.setLast(doctors.isLast());

        return doctorResponse;
    }

    /**
     * Creates a new doctor from the provided DTO.
     *
     * @param createDoctorDTO The data transfer object containing doctor details.
     * @return The created DoctorDTO with the doctor's details.
     */
    @Override
    public String createDoctor(CreateDoctorDTO createDoctorDTO) {
        validateCreateDoctorDTO(createDoctorDTO);

        UserEntity user = userRepo.findById(createDoctorDTO.getUserId())
                .orElseThrow(() -> new ObjectNotFoundException("No user found."));
        Hospital hospital = hospitalRepo.findById(createDoctorDTO.getHospitalId())
                .orElseThrow(() -> new ObjectNotFoundException("No hospital found."));

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(createDoctorDTO.getSpecialization());
        doctor.setHospital(hospital);

        user.setDoctor(doctor);

        doctorRepo.save(doctor);

        return String.format("User: %d is now assigned as a Doctor with specialization: %s", createDoctorDTO.getUserId(), createDoctorDTO.getSpecialization());
    }

    /**
     * Retrieves a doctor by their unique ID.
     *
     * @param id The unique identifier of the doctor.
     * @return The DoctorDTO with the doctor's details.
     */
    @Override
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );
        return mapToDto(doctor);
    }

    /**
     * Updates an existing doctor's details by their unique ID.
     *
     * @param updateDoctorDTO The data transfer object containing updated doctor details.
     * @param id The unique identifier of the doctor to update.
     * @return The updated DoctorDTO with the doctor's new details.
     */
    @Override
    public DoctorDTO updateDoctor(UpdateDoctorDTO updateDoctorDTO, Long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );

        if (!updateDoctorDTO.getSpecialization().toString().isEmpty()) {
            doctor.setSpecialization(updateDoctorDTO.getSpecialization());
        }

        return mapToDto(doctorRepo.save(doctor));
    }

    /**
     * Deletes a doctor identified by their unique ID.
     *
     * @param id The unique identifier of the doctor to be deleted.
     */
    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format("No doctor with id: %d was found.", id)
                )
        );

        doctorRepo.delete(doctor);
    }

    /**
     * Retrieves all doctors associated with a specific hospital by the hospital's ID.
     *
     * @param id The unique identifier of the hospital.
     * @return A list of DoctorDTOs with the details of the doctors working at the hospital.
     */
    @Override
    public List<DoctorDTO> getDoctorsFromHospitalById(Long id) {
        List<Doctor> doctors = doctorRepo.findByHospitalId(id);
        return doctors.stream().map(doctor -> mapToDto(doctor)).collect(Collectors.toList());
    }

    /**
     * Adds a doctor to a hospital by their respective IDs.
     *
     * @param doctorId The unique identifier of the doctor.
     * @param hospitalId The unique identifier of the hospital.
     * @return The updated DoctorDTO with the doctor's details.
     */
    @Override
    public DoctorDTO assignDoctorToAnotherHospital(Long doctorId, Long hospitalId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(
                () -> new ObjectNotFoundException("No doctor was found.")
        );

        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new ObjectNotFoundException("No hospital was found.")
        );

        if (!hospital.getDoctors().contains(doctor)) {
            hospital.getDoctors().add(doctor);
            doctor.setHospital(hospital);

            doctorRepo.save(doctor);
        } else {
            throw new InvalidArgumentsException("Doctor already works in this hospital.");
        }

        return mapToDto(doctor);
    }

    /**
     * Removes a doctor from a hospital by their respective IDs.
     *
     * @param doctorId The unique identifier of the doctor.
     * @param hospitalId The unique identifier of the hospital.
     */
    @Override
    public void removeDoctorFromHospital(Long doctorId, Long hospitalId) {
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
            throw new InvalidArgumentsException("Doctor does not work in this hospital.");
        }
    }

    @Override
    public void finishAppointment(Long doctorId, Long appointId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(
                () -> new ObjectNotFoundException("No such doctor.")
        );

        Appointment appointment = appointmentRepo.findById(appointId).orElseThrow(
                () -> new ObjectNotFoundException("No such appointment.")
        );

        if (!appointment.getDoctor().equals(doctor)) {
            throw new InvalidArgumentsException("Doctor is not assigned to appointment.");
        }

        appointment.setAppointmentStatus(AppointmentStatus.ARCHIVED);
        appointmentRepo.save(appointment);
    }

    @Override
    public void createOrder(Long doctorId, Long hospitalPharmacyId, CreateOrderDTO createOrderDTO) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(
                () -> new ObjectNotFoundException("Doctor not found.")
        );

        HospitalPharmacy hospitalPharmacy = hospitalPharmacyRepo.findById(hospitalPharmacyId).orElseThrow(
                () -> new ObjectNotFoundException("Hospital pharmacy not found.")
        );

        if (!doctor.getHospital().equals(hospitalPharmacy.getHospital())) {
            throw new InvalidArgumentsException("Doctor and hospital pharmacy are in different hospitals");
        }

        OrderEntity order = OrderEntity.builder()
                .dateTimeOfIssue(LocalDateTime.now())
                .orderBody(createOrderDTO.getOrderBody())
                .orderStatus(OrderStatus.ONGOING)
                .doctor(doctor)
                .hospitalPharmacy(hospitalPharmacy)
                .build();

        orderRepo.save(order);
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
        doctorDTO.setSpecialization(doctor.getSpecialization());
        doctorDTO.setSpecialization(doctor.getSpecialization());

        HospitalDoctorCompDTO hospitalDTO = new HospitalDoctorCompDTO();
        hospitalDTO.setId(doctor.getHospital().getId());
        hospitalDTO.setName(doctor.getHospital().getName());
        hospitalDTO.setAddress(doctor.getHospital().getAddress());
        doctorDTO.setHospital(hospitalDTO);
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

    private void validateCreateDoctorDTO(CreateDoctorDTO createDoctorDTO) {
        if (createDoctorDTO.getUserId() == 0 ||
                createDoctorDTO.getHospitalId() == 0 ||
                createDoctorDTO.getSpecialization() == null) {
            throw new InvalidArgumentsException("All fields (userId, hospitalId, and specialization) must be filled");
        }
    }
}
