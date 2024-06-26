package tbektenov.com.sau.dtos.patient;

import lombok.Data;
import tbektenov.com.sau.models.user.userRoles.BloodGroup;
import tbektenov.com.sau.models.user.userRoles.RhFactor;

/**
 * Data Transfer Object (DTO) for updating patient details.
 *
 * This DTO contains the necessary details to update the Rh factor and blood group
 * of an existing patient.
 */
@Data
public class UpdatePatientDTO {
    private RhFactor rhFactor;
    private BloodGroup bloodGroup;
}
