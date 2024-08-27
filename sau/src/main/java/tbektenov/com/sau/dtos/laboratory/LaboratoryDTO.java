package tbektenov.com.sau.dtos.laboratory;

import lombok.Data;

/**
 * DTO for representing a Laboratory.
 *
 * Fields:
 * - {@code id}: The unique identifier of the laboratory.
 * - {@code floor}: The floor number where the laboratory is located.
 */
@Data
public class LaboratoryDTO {
    private Long id;
    private int floor;
}
