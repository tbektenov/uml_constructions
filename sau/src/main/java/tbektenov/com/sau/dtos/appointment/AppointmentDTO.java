package tbektenov.com.sau.dtos.appointment;

import lombok.Data;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.models.AppointmentStatus;
import tbektenov.com.sau.models.user.userRoles.Specialization;

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
    private Specialization specialization;
    private String hospital;
    private String hospitalAddress;
    private AppointmentStatus appointmentStatus;
    private LocalDate date;
}
