package tbektenov.com.sau.dtos.user;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for User.
 *
 * This DTO represents the basic details of a user,
 * including their unique identifier, first name, and surname.
 */
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
}
