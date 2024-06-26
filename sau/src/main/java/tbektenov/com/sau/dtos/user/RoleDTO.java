package tbektenov.com.sau.dtos.user;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for a user role.
 *
 * This DTO contains the necessary information to represent a user role,
 * specifically the name of the role.
 */
@Data
public class RoleDTO {
    private String roleName;
}
