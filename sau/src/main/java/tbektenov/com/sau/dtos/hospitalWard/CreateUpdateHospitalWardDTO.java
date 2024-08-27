package tbektenov.com.sau.dtos.hospitalWard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a hospital ward.
 *
 * Fields:
 * - {@code wardNum}: The ward number.
 * - {@code capacity}: The capacity of the ward.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateHospitalWardDTO {
    private String wardNum;
    private int capacity;
}
