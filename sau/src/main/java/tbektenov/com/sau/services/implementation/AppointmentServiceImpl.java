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
 * Provides methods to create and cancel appointments, as well as
 * to map between DTOs and entity objects.
 */
@Service
public class AppointmentServiceImpl
    implements IAppointmentService {
    private EntityManager entityManager;
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
                                  AppointmentRepo appointmentRepo,
                                  EntityManager entityManager) {
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.appointmentRepo = appointmentRepo;
        this.entityManager = entityManager;
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
        if (Objects.equals(createAppointmentDTO.getPatient_id(), createAppointmentDTO.getDoctor_id())) {
            throw new InvalidArgumentsException("Patient ID and Doctor ID cannot be the same");
        }

        try {
            Object[] result = (Object[]) entityManager.createNativeQuery(
                            "select p.patient_id, d.doctor_id " +
                                    "from Patient p, Doctor d " +
                                    "WHERE p.patient_id = :patientId AND d.doctor_id = :doctorId"
                    )
                    .setParameter("patientId", createAppointmentDTO.getPatient_id())
                    .setParameter("doctorId", createAppointmentDTO.getDoctor_id())
                    .getSingleResult();

            Appointment appointment = new Appointment();
            appointment.setDate(createAppointmentDTO.getDate());
            appointment.setAppointmentStatus(createAppointmentDTO.getAppointmentStatus());
            appointment.setPatient(entityManager.getReference(Patient.class, createAppointmentDTO.getPatient_id()));
            appointment.setDoctor(entityManager.getReference(Doctor.class, createAppointmentDTO.getDoctor_id()));

            Appointment newAppointment = appointmentRepo.save(appointment);

            return mapToDto(newAppointment);
        } catch (NoResultException e) {
            throw new ObjectNotFoundException("Patient or Doctor not found");
        }
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
