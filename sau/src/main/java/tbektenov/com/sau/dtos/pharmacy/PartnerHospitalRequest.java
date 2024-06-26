package tbektenov.com.sau.dtos.pharmacy;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for requesting to add or remove a partner hospital.
 *
 * This DTO contains the necessary detail to specify a hospital by its unique identifier
 * for operations involving the addition or removal of partner hospitals to/from a pharmacy.
 */
@Data
public class PartnerHospitalRequest {
    private Long hospitalId;
}
