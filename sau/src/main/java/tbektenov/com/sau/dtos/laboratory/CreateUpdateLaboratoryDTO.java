package tbektenov.com.sau.dtos.laboratory;

import lombok.Data;

/**
 * DTO for creating or updating a Laboratory.
 *
 * Fields:
 * - {@code floor}: The floor number where the laboratory is located.
 */
@Data
public class CreateUpdateLaboratoryDTO {
    private int floor;
}
