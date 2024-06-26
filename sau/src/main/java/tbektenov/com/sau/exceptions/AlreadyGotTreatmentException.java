package tbektenov.com.sau.exceptions;

import java.io.Serial;

public class AlreadyGotTreatmentException
        extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 4L;

    /**
     * Constructs a new {@code ObjectNotFoundException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public AlreadyGotTreatmentException(String message) {
        super(message);
    }
}
