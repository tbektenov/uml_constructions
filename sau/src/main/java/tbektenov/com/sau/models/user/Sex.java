package tbektenov.com.sau.models.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

/**
 * Enum representing the sex of a user.
 *
 * <p>This enum defines the possible values for the sex of a user, including
 * MALE, FEMALE, and OTHER.</p>
 */
public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    private String name;

    Sex(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the sex.
     *
     * @return the name of the sex.
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Converts a string value to a Sex enum.
     *
     * <p>This method is used during deserialization of JSON data.</p>
     *
     * @param value the string value representing the sex.
     * @return the corresponding Sex enum.
     * @throws InvalidArgumentsException if the value does not match any Sex.
     */
    @JsonCreator
    public static Sex fromValue(String value) {
        for (Sex sex : Sex.values()) {
            if (sex.name.equalsIgnoreCase(value)) return sex;
        }

        throw new InvalidArgumentsException(String.format("No sex: %s", value));
    }
}
