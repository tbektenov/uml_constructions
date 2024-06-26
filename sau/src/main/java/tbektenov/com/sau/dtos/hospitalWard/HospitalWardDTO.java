package tbektenov.com.sau.dtos.hospitalWard;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for Hospital Ward.
 *
 * This DTO represents the detailed information of a hospital ward,
 * including its unique identifier, ward number, and capacity.
 */
@Data
public class HospitalWardDTO {
    private Long id;
    private String wardNum;
    private int capacity;
}
