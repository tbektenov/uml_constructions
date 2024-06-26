package tbektenov.com.sau.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a requested object is not found.
 *
 * <p>This exception extends {@link RuntimeException} and is used to indicate that
 * a specific object was not found within the application. It includes a detailed
 * error message that can be used to inform the user or log the issue.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     if (object == null) {
 *         throw new ObjectNotFoundException("Object with ID " + id + " not found.");
 *     }
 * </pre>
 *
 * @see java.lang.RuntimeException
 */
public class ObjectNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code ObjectNotFoundException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
