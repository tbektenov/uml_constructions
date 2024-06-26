package tbektenov.com.sau.exceptions;

import java.io.Serial;

/**
 * Exception thrown when invalid arguments are provided to a hospital-related operation.
 *
 * <p>This exception is a runtime exception and is used to indicate that
 * the arguments provided to a method or operation are invalid or inappropriate.</p>
 */
public class InvalidArgumentsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2;

    /**
     * Constructs a new {@code HospitalInvalidArgumentsException} with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
