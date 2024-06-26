package tbektenov.com.sau.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

public enum OrderStatus {
    ONGOING("ONGOING"),
    COMPLETED("COMPLETED");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name.equalsIgnoreCase(value)) return orderStatus;
        }

        throw new InvalidArgumentsException(String.format("Unknown order status: %s", value));
    }
}
