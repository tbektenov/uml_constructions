package tbektenov.com.sau.models.user.userRoles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

public enum Specialization {
    DENTIST("DENTIST"),
    PSYCHOLOGIST("PSYCHOLOGIST"),
    VETERINARIAN("VETERINARIAN"),
    OPHTHALMOLOGIST("OPHTHALMOLOGIST");

    private String name;

    Specialization(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Specialization fromValue(String value) {
        for (Specialization specialization : Specialization.values()) {
            if (specialization.name.equalsIgnoreCase(value)) return specialization;
        }

        throw new InvalidArgumentsException(String.format("Unknown role: %s", value));
    }
}
