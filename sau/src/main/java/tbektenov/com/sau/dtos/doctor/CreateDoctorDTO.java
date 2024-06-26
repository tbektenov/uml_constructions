package tbektenov.com.sau.dtos.doctor;

import lombok.Data;
import tbektenov.com.sau.models.user.userRoles.Specialization;

/**
 * Data Transfer Object (DTO) for creating a new doctor.
 *
 * This DTO contains the necessary details to assign a doctor role to a user,
 * including the user's specialization and their unique user ID.
 */
@Data
public class CreateDoctorDTO {
    private Specialization specialization;
    private Long userId;
    private Long hospitalId;
}
