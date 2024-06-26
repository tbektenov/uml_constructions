package tbektenov.com.sau.models.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.user.userRoles.BloodGroup;

public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    private String name;

    Sex(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Sex fromValue(String value) {
        for (Sex sex : Sex.values()) {
            if (sex.name.equalsIgnoreCase(value)) return sex;
        }

        throw new InvalidArgumentsException(String.format("No sex: %s", value));
    }
}
