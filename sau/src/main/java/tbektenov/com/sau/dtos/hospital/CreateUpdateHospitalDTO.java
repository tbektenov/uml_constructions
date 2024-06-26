package tbektenov.com.sau.dtos.hospital;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for creating a new hospital.
 * This class encapsulates the necessary information
 * required to create a hospital entity.
 *
 * <p>Uses Lombok's {@code @Data} annotation to automatically generate
 * getter, setter, toString, equals, and hashCode methods.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code name}: The name of the hospital (cannot be null or empty).</li>
 *   <li>{@code address}: The address of the hospital (cannot be null or empty).</li>
 * </ul>
 *
 * <p>Example:</p>
 * <pre>
 *     CreateHospitalDTO hospitalDTO = new CreateHospitalDTO();
 *     hospitalDTO.setName("City Hospital");
 *     hospitalDTO.setAddress("123 Main St, Anytown, USA");
 * </pre>
 *
 * @see lombok.Data
 */
@Data
public class CreateUpdateHospitalDTO {
    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Address cannot be null or empty")
    private String address;
}
