package tbektenov.com.sau.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.repositories.AppointmentRepo;
import tbektenov.com.sau.repositories.DoctorRepo;
import tbektenov.com.sau.repositories.PatientRepo;
import tbektenov.com.sau.services.IAppointmentService;

import java.util.List;
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
    public AppointmentServiceImpl(DoctorRepo doctorRepo, PatientRepo patientRepo, AppointmentRepo appointmentRepo) {
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
    public AppointmentDTO createAppointment(CreateAppointmentDTO createAppointmentDTO) {
        Appointment appointment = mapToEntity(createAppointmentDTO);

        Appointment newAppointment = appointmentRepo.save(appointment);
        return mapToDto(newAppointment);
    }

    /**
     * Cancels an appointment identified by its ID.
     *
     * @param id The unique identifier of the appointment to be cancelled.
     */
    @Override
    public void cancelAppointmentById(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("No such appointment.")
        );

        appointmentRepo.delete(appointment);
    }

    @Override
    public List<AppointmentDTO> getUpcomingAppointmentsByPatientId(Long patient_id) {
        List<Appointment> appointments = appointmentRepo.findUpcomingByPatientId(patient_id);
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
        appointmentDTO.setPatient_id(appointment.getPatient().getId());
        appointmentDTO.setDoctor_id(appointment.getDoctor().getId());
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
        Patient patient = patientRepo.findById(createAppointmentDTO.getPatient_id()).orElseThrow(() -> new ObjectNotFoundException("No such patient."));
        appointment.setPatient(patient);

        Doctor doctor = doctorRepo.findById(createAppointmentDTO.getDoctor_id()).orElseThrow(() -> new ObjectNotFoundException("No such doctor."));
        appointment.setDoctor(doctor);

        appointment.setDate(createAppointmentDTO.getDate());
        appointment.setAppointmentStatus(createAppointmentDTO.getAppointmentStatus());
        return appointment;
    }
}
