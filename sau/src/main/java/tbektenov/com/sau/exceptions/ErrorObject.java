package tbektenov.com.sau.exceptions;

import lombok.Data;
import java.util.Date;

/**
 * Encapsulates details about an error or exception.
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code statusCode}: HTTP status code representing the error.</li>
 *   <li>{@code message}: Descriptive message about the error.</li>
 *   <li>{@code timestamp}: Date and time when the error occurred.</li>
 * </ul>
 *
 * <p>Example:</p>
 * <pre>
 *     ErrorObject error = new ErrorObject(404, "Resource not found", new Date());
 * </pre>
 *
 * @see lombok.Data
 */
@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}
