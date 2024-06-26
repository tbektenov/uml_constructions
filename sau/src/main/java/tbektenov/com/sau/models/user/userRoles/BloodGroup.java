package tbektenov.com.sau.models.user.userRoles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

public enum BloodGroup {
    A("A"),
    B("B"),
    AB("AB"),
    O("O");

    private String name;

    BloodGroup(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static BloodGroup fromValue(String value) {
        for (BloodGroup bloodGroup : BloodGroup.values()) {
            if (bloodGroup.name.equalsIgnoreCase(value)) return bloodGroup;
        }

        throw new InvalidArgumentsException(String.format("No such blood group: %s", value));
    }
}
