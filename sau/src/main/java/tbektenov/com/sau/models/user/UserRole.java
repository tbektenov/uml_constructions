package tbektenov.com.sau.models.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

public enum UserRole {
    DOCTOR("DOCTOR"),
    PATIENT("PATIENT"),
    NURSE("NURSE");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static UserRole fromValue(String value) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name.equalsIgnoreCase(value)) return userRole;
        }

        throw new InvalidArgumentsException(String.format("No user role: %s", value));
    }
}
