package tbektenov.com.sau.dtos.doctor;

import lombok.Data;
import tbektenov.com.sau.models.user.userRoles.Specialization;

/**
 * Data Transfer Object (DTO) for updating doctor details.
 *
 * This DTO contains the information needed to update the medical specialization
 * of an existing doctor.
 */
@Data
public class UpdateDoctorDTO {
    private Specialization specialization;
}
