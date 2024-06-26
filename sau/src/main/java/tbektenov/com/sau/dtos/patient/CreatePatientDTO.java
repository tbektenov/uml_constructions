package tbektenov.com.sau.dtos.patient;

import lombok.Data;
import tbektenov.com.sau.models.user.userRoles.BloodGroup;
import tbektenov.com.sau.models.user.userRoles.RhFactor;

/**
 * Data Transfer Object (DTO) for creating a new patient.
 *
 * This DTO contains the necessary details to assign a patient role to a user,
 * including their Rh factor, blood group, and the unique user ID.
 */
@Data
public class CreatePatientDTO {
    private RhFactor rhFactor;
    private BloodGroup bloodGroup;
    private Long userId;
}
