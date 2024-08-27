package tbektenov.com.sau.dtos.hospitalWard;

import lombok.Data;

/**
 * DTO for Hospital Ward details.
 *
 * Fields:
 * - {@code id}: Unique identifier of the ward.
 * - {@code wardNum}: The ward number.
 * - {@code capacity}: The capacity of the ward.
 */
@Data
public class HospitalWardDTO {
    private Long id;
    private String wardNum;
    private int capacity;
}
