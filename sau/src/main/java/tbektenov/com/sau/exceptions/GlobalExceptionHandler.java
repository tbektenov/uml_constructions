package tbektenov.com.sau.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Global handler for application-wide exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ObjectNotFoundException} and returns a 404 response.
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
     * Handles {@link InvalidArgumentsException} and returns a 400 response.
     */
    @ExceptionHandler(InvalidArgumentsException.class)
    public ResponseEntity<ErrorObject> handleInvalidArgumentsException(
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
     * Handles {@link UserHasSuchRoleException} and returns a 400 response.
     */
    @ExceptionHandler(UserHasSuchRoleException.class)
    public ResponseEntity<ErrorObject> handleUserHasSuchRoleException(
            UserHasSuchRoleException ex,
            WebRequest req
    ) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link AlreadyGotTreatmentException} and returns a 208 response.
     */
    @ExceptionHandler(AlreadyGotTreatmentException.class)
    public ResponseEntity<ErrorObject> handleAlreadyGotTreatmentException(
            AlreadyGotTreatmentException ex,
            WebRequest req
    ) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.ALREADY_REPORTED.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.ALREADY_REPORTED);
    }
}
