package tbektenov.com.sau.dtos.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tbektenov.com.sau.models.AppointmentStatus;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for creating a new appointment.
 *
 * This DTO contains the necessary details to schedule a new appointment,
 * including the patient and doctor identifiers, the appointment date,
 * and the initial status.
 */
@Data
public class CreateAppointmentDTO {
    @NotNull(message = "Patient cannot be null.")
    private Long patient_id;
    @NotNull(message = "Doctor cannot be null.")
    private Long doctor_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private AppointmentStatus appointmentStatus = AppointmentStatus.UPCOMING;
}
