package tbektenov.com.sau.models.user.userRoles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

/**
 * Enum representing the specializations for a doctor.
 *
 * <p>This enum defines the various specializations that a doctor can have, such as
 * DENTIST, PSYCHOLOGIST, VETERINARIAN, and OPHTHALMOLOGIST.</p>
 */
public enum Specialization {
    DENTIST("DENTIST"),
    PSYCHOLOGIST("PSYCHOLOGIST"),
    VETERINARIAN("VETERINARIAN"),
    OPHTHALMOLOGIST("OPHTHALMOLOGIST");

    private String name;

    Specialization(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the specialization.
     *
     * @return the name of the specialization.
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Converts a string value to a Specialization enum.
     *
     * <p>This method is used during deserialization of JSON data.</p>
     *
     * @param value the string value representing the specialization.
     * @return the corresponding Specialization enum.
     * @throws InvalidArgumentsException if the value does not match any Specialization.
     */
    @JsonCreator
    public static Specialization fromValue(String value) {
        for (Specialization specialization : Specialization.values()) {
            if (specialization.name.equalsIgnoreCase(value)) return specialization;
        }

        throw new InvalidArgumentsException(String.format("Unknown specialization: %s", value));
    }
}
