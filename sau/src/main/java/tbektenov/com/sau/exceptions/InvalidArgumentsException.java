package tbektenov.com.sau.exceptions;

import java.io.Serial;

/**
 * Exception thrown for invalid arguments in operations.
 */
public class InvalidArgumentsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2L;

    /**
     * Constructs a new {@code InvalidArgumentsException} with the specified message.
     *
     * @param message the detail message
     */
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
