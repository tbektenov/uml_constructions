package tbektenov.com.sau.dtos.hospitalWard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating or updating a hospital ward.
 *
 * This DTO contains the necessary details to create a new hospital ward or update an existing one,
 * including the ward number and its capacity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateHospitalWardDTO {
    private String wardNum;
    private int capacity;
}
