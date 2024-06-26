package tbektenov.com.sau.dtos.laboratory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for creating or updating a Laboratory entity.
 *
 * <p>This DTO is used to transfer data between the client and server when creating or updating
 * a laboratory associated with a hospital. It includes necessary attributes for specifying
 * the details of a laboratory.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@link Data}: Lombok annotation to generate boilerplate code such as getters, setters, and toString.</li>
 *   <li>{@link NoArgsConstructor}: Lombok annotation to generate a no-arguments constructor.</li>
 *   <li>{@link AllArgsConstructor}: Lombok annotation to generate a constructor with arguments for all fields.</li>
 *   <li>{@link Builder}: Lombok annotation to provide a builder pattern implementation for the class.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 *     // Using builder pattern to create an instance
 *     CreateUpdateLaboratoryDTO dto = CreateUpdateLaboratoryDTO.builder()
 *                                      .floor(3)
 *                                      .build();
 *
 *     // Using all-args constructor to create an instance
 *     CreateUpdateLaboratoryDTO dto2 = new CreateUpdateLaboratoryDTO(2);
 * </pre>
 *
 * @see lombok.Data
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @see lombok.Builder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateLaboratoryDTO {
    private int floor;
}
