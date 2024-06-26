package tbektenov.com.sau.dtos.laboratory;

import lombok.Data;

/**
 * Data Transfer Object for the Laboratory entity.
 *
 * <p>This DTO is used to transfer data related to a Laboratory between the server and client.
 * It includes necessary attributes for representing a laboratory, typically used for reading
 * and presenting data rather than for creating or updating.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@link Data}: Lombok annotation to automatically generate boilerplate code such as getters, setters,
 *       and toString, equals, and hashCode methods.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 *     // Creating an instance and setting fields
 *     LaboratoryDTO laboratory = new LaboratoryDTO();
 *     laboratory.setId(1);
 *     laboratory.setFloor(3);
 *
 *     // Accessing fields
 *     int id = laboratory.getId();
 *     int floor = laboratory.getFloor();
 * </pre>
 *
 * @see lombok.Data
 */
@Data
public class LaboratoryDTO {
    private Long id;
    private int floor;
}
