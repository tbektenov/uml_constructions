package tbektenov.com.sau.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.user.userRoles.Specialization;

public enum AppointmentStatus {
    UPCOMING("UPCOMING"),
    ARCHIVED("ARCHIVED");

    private String name;

    AppointmentStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static AppointmentStatus fromValue(String value) {
        for (AppointmentStatus appointmentStatus : AppointmentStatus.values()) {
            if (appointmentStatus.name.equalsIgnoreCase(value)) return appointmentStatus;
        }

        throw new InvalidArgumentsException(String.format("Unknown appointment status: %s", value));
    }
}
