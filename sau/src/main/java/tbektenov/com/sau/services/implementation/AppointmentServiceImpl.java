package tbektenov.com.sau.services.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.AppointmentStatus;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.repositories.AppointmentRepo;
import tbektenov.com.sau.repositories.DoctorRepo;
import tbektenov.com.sau.repositories.PatientRepo;
import tbektenov.com.sau.services.IAppointmentService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation for managing appointments.
 *
 * Provides methods to create, cancel, and archive appointments,
 * as well as to retrieve upcoming appointments for a patient.
 */
@Service
public class AppointmentServiceImpl
        implements IAppointmentService {
    private DoctorRepo doctorRepo;
    private PatientRepo patientRepo;
    private AppointmentRepo appointmentRepo;

    /**
     * Constructs an AppointmentServiceImpl with the specified repositories.
     *
     * @param doctorRepo Repository for Doctor entities.
     * @param patientRepo Repository for Patient entities.
     * @param appointmentRepo Repository for Appointment entities.
     */
    @Autowired
    public AppointmentServiceImpl(DoctorRepo doctorRepo,
                                  PatientRepo patientRepo,
                                  AppointmentRepo appointmentRepo) {
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.appointmentRepo = appointmentRepo;
    }

    /**
     * Creates a new appointment from the provided DTO.
     *
     * @param createAppointmentDTO The data transfer object containing appointment details.
     * @return The created AppointmentDTO with the appointment's details.
     */
    @Override
    @Transactional
    public AppointmentDTO createAppointment(CreateAppointmentDTO createAppointmentDTO) {
        System.out.println("Received DTO: " + createAppointmentDTO);

        if (Objects.equals(createAppointmentDTO.getPatient_id(), createAppointmentDTO.getDoctor_id())) {
            throw new InvalidArgumentsException("Patient ID and Doctor ID cannot be the same");
        }

        if (createAppointmentDTO.getDate() == null) {
            throw new InvalidArgumentsException("Date cannot be null");
        }

        Patient patient = patientRepo.findById(createAppointmentDTO.getPatient_id()).orElseThrow(
                () -> new ObjectNotFoundException("Patient not found")
        );

        Doctor doctor = doctorRepo.findById(createAppointmentDTO.getDoctor_id()).orElseThrow(
                () -> new ObjectNotFoundException("Doctor not found")
        );

        Appointment appointment = new Appointment();
        appointment.setDate(createAppointmentDTO.getDate());
        appointment.setAppointmentStatus(createAppointmentDTO.getAppointmentStatus());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment newAppointment = appointmentRepo.save(appointment);

        return mapToDto(newAppointment);
    }

    /**
     * Cancels an appointment identified by its ID.
     *
     * @param id The unique identifier of the appointment to be cancelled.
     */
    @Override
    @Transactional
    public void cancelAppointmentById(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("No such appointment.")
        );

        appointmentRepo.delete(appointment);
    }

    /**
     * Archives an appointment identified by its ID.
     *
     * @param id The unique identifier of the appointment to be archived.
     */
    @Override
    @Transactional
    public void archiveAppointmentById(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("No such appointment.")
        );

        if (appointment.getAppointmentStatus().equals(AppointmentStatus.ARCHIVED)) {
            throw new InvalidArgumentsException("Appointment is already archived");
        }

        appointment.setAppointmentStatus(AppointmentStatus.ARCHIVED);
        appointmentRepo.save(appointment);
    }

    /**
     * Retrieves a list of upcoming appointments for a specific patient.
     *
     * @param patient_id The ID of the patient.
     * @return A list of upcoming AppointmentDTOs.
     */
    @Override
    @Transactional
    public List<AppointmentDTO> getUpcomingAppointmentsByPatientId(Long patient_id) {
        List<Appointment> appointments = appointmentRepo.findByPatientIdAndAppointmentStatus(
                patient_id, AppointmentStatus.UPCOMING
        );

        return appointments.stream().map(appointment -> mapToDto(appointment)).collect(Collectors.toList());
    }

    /**
     * Maps an Appointment entity to an AppointmentDTO.
     *
     * @param appointment The Appointment entity to map.
     * @return The mapped AppointmentDTO with the appointment's details.
     */
    private AppointmentDTO mapToDto(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setSpecialization(appointment.getDoctor().getSpecialization());
        appointmentDTO.setHospital(appointment.getDoctor().getHospital().getName());
        appointmentDTO.setHospitalAddress(appointment.getDoctor().getHospital().getAddress());
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setAppointmentStatus(appointment.getAppointmentStatus());

        return appointmentDTO;
    }

    /**
     * Maps a CreateAppointmentDTO to an Appointment entity.
     *
     * @param createAppointmentDTO The data transfer object containing appointment details.
     * @return The mapped Appointment entity with the details from the DTO.
     */
    private Appointment mapToEntity(CreateAppointmentDTO createAppointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setDate(createAppointmentDTO.getDate());
        appointment.setAppointmentStatus(createAppointmentDTO.getAppointmentStatus());
        return appointment;
    }
}
