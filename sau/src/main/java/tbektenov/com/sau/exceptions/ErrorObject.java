package tbektenov.com.sau.exceptions;

import lombok.Data;
import java.util.Date;

/**
 * Represents an error object that contains details about an error or exception.
 *
 * <p>This class is used to encapsulate information about errors or exceptions
 * that occur within the application. It is typically used in exception handling
 * to provide a structured response containing the error details.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code statusCode}: The HTTP status code representing the type of error.</li>
 *   <li>{@code message}: A descriptive message providing details about the error.</li>
 *   <li>{@code timestamp}: The date and time when the error occurred.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 *     ErrorObject error = new ErrorObject();
 *     error.setStatusCode(404);
 *     error.setMessage("Resource not found");
 *     error.setTimestamp(new Date());
 * </pre>
 *
 * <p>This example demonstrates how to create and populate an {@code ErrorObject} instance,
 * typically used in custom exception handlers to return detailed error information to clients.</p>
 *
 * @see lombok.Data
 */
@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}
