package tbektenov.com.sau.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an attempt is made to treat a patient who has already received treatment.
 */
public class AlreadyGotTreatmentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4L;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public AlreadyGotTreatmentException(String message) {
        super(message);
    }
}
