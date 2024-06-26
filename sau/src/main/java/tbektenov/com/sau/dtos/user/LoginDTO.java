package tbektenov.com.sau.dtos.user;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user login.
 *
 * This DTO contains the necessary details for a user to log in,
 * including their username and password.
 */
@Data
public class LoginDTO {
    private String username;
    private String password;
}
