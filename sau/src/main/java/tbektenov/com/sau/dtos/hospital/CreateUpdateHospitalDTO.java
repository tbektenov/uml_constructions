package tbektenov.com.sau.dtos.hospital;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating or updating a hospital.
 *
 * Fields:
 * - {@code name}: The name of the hospital (required).
 * - {@code address}: The address of the hospital (required).
 */
@Data
public class CreateUpdateHospitalDTO {
    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Address cannot be null or empty")
    private String address;
}
