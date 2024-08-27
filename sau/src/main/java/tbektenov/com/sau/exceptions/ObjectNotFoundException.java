package tbektenov.com.sau.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a requested object is not found.
 */
public class ObjectNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code ObjectNotFoundException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
