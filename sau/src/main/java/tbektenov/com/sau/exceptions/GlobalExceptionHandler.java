package tbektenov.com.sau.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Global exception handler for handling application-wide exceptions.
 *
 * <p>This class is annotated with {@code @ControllerAdvice} to handle exceptions globally
 * across the whole application. It provides a centralized mechanism to handle specific
 * exceptions and return custom error responses.</p>
 *
 * <p>Exception Handlers:</p>
 * <ul>
 *   <li>{@link #handleObjectNotFoundException(ObjectNotFoundException, WebRequest)}:
 *       Handles {@link ObjectNotFoundException} and returns a custom error response.</li>
 * </ul>
 *
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 * @see tbektenov.com.sau.exceptions.ErrorObject
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ObjectNotFoundException} and returns a custom error response.
     *
     * @param ex  the exception thrown when an object is not found
     * @param req the web request during which the exception occurred
     * @return a {@link ResponseEntity} containing a custom {@link ErrorObject} and HTTP status
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorObject> handleObjectNotFoundException(
            ObjectNotFoundException ex,
            WebRequest req
    ) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link InvalidArgumentsException} exceptions.
     *
     * @param ex  the exception
     * @param req the web request
     * @return a {@link ResponseEntity} containing an {@link ErrorObject} and a 400 Bad Request status
     */
    @ExceptionHandler(InvalidArgumentsException.class)
    public ResponseEntity<ErrorObject> handleHospitalInvalidArgumentsException(
            InvalidArgumentsException ex,
            WebRequest req
    ) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserHasSuchRoleException exceptions and returns a structured error response.
     *
     * This method catches UserHasSuchRoleException and constructs an ErrorObject
     * to provide a standardized response format with a status code, message, and timestamp.
     *
     * @param ex   The exception thrown indicating that a user already has the specified role.
     * @param req  The web request during which the exception occurred.
     * @return A ResponseEntity containing the ErrorObject with HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(UserHasSuchRoleException.class)
    public ResponseEntity<ErrorObject> handleUserHasSuchRoleException(
            InvalidArgumentsException ex,
            WebRequest req
    ) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyGotTreatmentException.class)
    public ResponseEntity<ErrorObject> handleAlreadyGotTreatmentException(
            InvalidArgumentsException ex,
            WebRequest req
    ) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.ALREADY_REPORTED.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.ALREADY_REPORTED);
    }
}
