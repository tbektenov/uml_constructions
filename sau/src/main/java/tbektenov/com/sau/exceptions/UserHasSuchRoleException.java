package tbektenov.com.sau.exceptions;

import java.io.Serial;

/**
 * Custom exception for cases where a user already has the specified role.
 *
 * This exception is thrown when an operation attempts to assign a role to a user
 * that they already possess, indicating a redundant or invalid assignment.
 */
public class UserHasSuchRoleException
    extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 3;

    public UserHasSuchRoleException(String message) {
        super(message);
    }
}
