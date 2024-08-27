package tbektenov.com.sau.models.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

/**
 * Enum representing different user roles in the system.
 *
 * <p>Roles include DOCTOR, PATIENT, and NURSE.</p>
 *
 * <p>Provides methods for converting role names to enum values and vice versa.</p>
 */
public enum UserRole {
    DOCTOR("DOCTOR"),
    PATIENT("PATIENT"),
    NURSE("NURSE");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    /**
     * Returns the role name as a string.
     *
     * @return the role name
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Converts a string value to a UserRole enum.
     *
     * <p>This method is used during deserialization of JSON data.</p>
     *
     * @param value the role name as a string
     * @return the corresponding UserRole
     * @throws InvalidArgumentsException if the role name is not valid
     */
    @JsonCreator
    public static UserRole fromValue(String value) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name.equalsIgnoreCase(value)) return userRole;
        }

        throw new InvalidArgumentsException(String.format("No user role: %s", value));
    }
}
