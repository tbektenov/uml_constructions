package tbektenov.com.sau.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;

/**
 * Enum representing the status of an order.
 */
public enum OrderStatus {
    ONGOING("ONGOING"),
    COMPLETED("COMPLETED");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the order status.
     *
     * @return the name of the order status
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Creates an {@code OrderStatus} from its string representation.
     *
     * <p>This method is used during deserialization of JSON data.</p>
     *
     * @param value the string representation of the order status
     * @return the corresponding {@code OrderStatus}
     * @throws InvalidArgumentsException if the string does not match any known order status
     */
    @JsonCreator
    public static OrderStatus fromValue(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name.equalsIgnoreCase(value)) return orderStatus;
        }

        throw new InvalidArgumentsException(String.format("Unknown order status: %s", value));
    }
}
