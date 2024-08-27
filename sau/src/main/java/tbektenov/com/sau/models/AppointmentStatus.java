package tbektenov.com.sau.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.user.userRoles.Specialization;

/**
 * Enum representing the status of an appointment.
 *
 * <p>This enum defines the possible values for the status of an appointment, including
 * UPCOMING, and ARCHIVED.</p>
 */
public enum AppointmentStatus {
    UPCOMING("UPCOMING"),
    ARCHIVED("ARCHIVED");

    private String name;

    AppointmentStatus(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the status.
     *
     * @return the name of the status.
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Converts a string value to an AppointmentStatus enum.
     *
     * <p>This method is used during deserialization of JSON data.</p>
     *
     * @param value the string value representing the status.
     * @return the corresponding AppointmentStatus enum.
     * @throws InvalidArgumentsException if the value does not match any AppointmentStatus.
     */
    @JsonCreator
    public static AppointmentStatus fromValue(String value) {
        for (AppointmentStatus appointmentStatus : AppointmentStatus.values()) {
            if (appointmentStatus.name.equalsIgnoreCase(value)) return appointmentStatus;
        }

        throw new InvalidArgumentsException(String.format("Unknown appointment status: %s", value));
    }
}
