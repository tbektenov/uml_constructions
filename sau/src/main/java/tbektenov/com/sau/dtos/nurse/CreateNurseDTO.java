package tbektenov.com.sau.dtos.nurse;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for creating a new nurse.
 *
 * This DTO contains the necessary detail to assign a nurse role to a user,
 * specifically the unique identifier of the user.
 */
@Data
public class CreateNurseDTO {
    private Long userId;
}
