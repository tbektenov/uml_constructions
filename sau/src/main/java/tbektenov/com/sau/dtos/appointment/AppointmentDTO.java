package tbektenov.com.sau.dtos.appointment;

import lombok.Data;
import tbektenov.com.sau.models.AppointmentStatus;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for Appointment.
 *
 * This DTO represents the essential details of an appointment,
 * including the patient and doctor involved, the date, and the status.
 */
@Data
public class AppointmentDTO {
    private Long id;
    private Long patient_id;
    private Long doctor_id;
    private LocalDate date;
    private AppointmentStatus appointmentStatus;
}
