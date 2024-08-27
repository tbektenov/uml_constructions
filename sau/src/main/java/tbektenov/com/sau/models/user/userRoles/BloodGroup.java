package tbektenov.com.sau.models.user.userRoles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

/**
 * Enum representing blood groups.
 */
public enum BloodGroup {
    A("A"),
    B("B"),
    AB("AB"),
    O("O");

    private final String name;

    BloodGroup(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the blood group.
     *
     * @return the name of the blood group
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Creates a BloodGroup enum from a given string value.
     *
     * <p>This method is used during deserialization of JSON data.</p>
     *
     * @param value the string value representing a blood group
     * @return the corresponding BloodGroup enum
     * @throws InvalidArgumentsException if the value does not match any blood group
     */
    @JsonCreator
    public static BloodGroup fromValue(String value) {
        for (BloodGroup bloodGroup : BloodGroup.values()) {
            if (bloodGroup.name.equalsIgnoreCase(value)) return bloodGroup;
        }
        throw new InvalidArgumentsException(String.format("No such blood group: %s", value));
    }
}
