package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;

import java.util.List;

/**
 * Service interface for handling appointment operations.
 *
 * This interface defines methods for creating an appointment and canceling an appointment by its ID.
 */
public interface IAppointmentService {
    /**
     * Creates a new appointment based on the provided details.
     *
     * @param createAppointmentDTO The data transfer object containing details for the new appointment.
     * @return The created AppointmentDTO with the appointment's details.
     */
    AppointmentDTO createAppointment(CreateAppointmentDTO createAppointmentDTO);

    /**
     * Retrieves a list of upcoming appointments for a specific patient.
     *
     * @param patient_id The ID of the patient.
     * @return A list of upcoming AppointmentDTOs.
     */
    List<AppointmentDTO> getUpcomingAppointmentsByPatientId(Long patient_id);
}
