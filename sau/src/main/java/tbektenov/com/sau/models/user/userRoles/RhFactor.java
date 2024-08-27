package tbektenov.com.sau.models.user.userRoles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

/**
 * Enum representing the Rh factor of a blood type.
 *
 * <p>The Rh factor can be either POSITIVE or NEGATIVE. This enum provides
 * methods to retrieve the Rh factor name and convert a string value to an Rh factor.</p>
 */
public enum RhFactor {
    POSITIVE("POSITIVE"),
    NEGATIVE("NEGATIVE");

    private String name;

    RhFactor(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the Rh factor.
     *
     * @return the name of the Rh factor.
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Converts a string value to an RhFactor enum.
     *
     * <p>This method is used during deserialization of JSON data.</p>
     *
     * @param value the string value representing the Rh factor.
     * @return the corresponding RhFactor enum.
     * @throws InvalidArgumentsException if the value does not match any RhFactor.
     */
    @JsonCreator
    public static RhFactor fromValue(String value) {
        for (RhFactor rhFactor : RhFactor.values()) {
            if (rhFactor.name.equalsIgnoreCase(value)) return rhFactor;
        }

        throw new InvalidArgumentsException(String.format("No such rh factor: %s", value));
    }
}
