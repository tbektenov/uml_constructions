package tbektenov.com.sau.dtos.nurse;

import lombok.Data;
import tbektenov.com.sau.dtos.user.UserDTO;

/**
 * Data Transfer Object (DTO) for Nurse.
 *
 * This DTO represents the detailed information of a nurse,
 * including their unique identifier and associated user details.
 */
@Data
public class NurseDTO {
    private Long id;
    private UserDTO userDTO;
}
