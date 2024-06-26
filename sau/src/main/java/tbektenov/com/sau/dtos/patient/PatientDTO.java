package tbektenov.com.sau.dtos.patient;

import lombok.Data;
import tbektenov.com.sau.dtos.user.UserDTO;
import tbektenov.com.sau.models.user.userRoles.BloodGroup;
import tbektenov.com.sau.models.user.userRoles.RhFactor;

/**
 * Data Transfer Object (DTO) for Patient.
 *
 * This DTO represents the detailed information of a patient,
 * including their unique identifier, Rh factor, blood group, and associated user details.
 */
@Data
public class PatientDTO {
    private Long id;
    private RhFactor rhFactor;
    private BloodGroup bloodGroup;
    private UserDTO user;
}
