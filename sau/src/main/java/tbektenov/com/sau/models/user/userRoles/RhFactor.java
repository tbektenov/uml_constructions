package tbektenov.com.sau.models.user.userRoles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

public enum RhFactor {
    POSITIVE("POSITIVE"),
    NEGATIVE("NEGATIVE");

    private String name;

    RhFactor(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static RhFactor fromValue(String value) {
        for (RhFactor rhFactor : RhFactor.values()) {
            if (rhFactor.name.equalsIgnoreCase(value)) return rhFactor;
        }

        throw new InvalidArgumentsException(String.format("No such rh factor: %s", value));
    }
}
