package tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for Hospital Pharmacy.
 *
 * This DTO represents the detailed information of a hospital pharmacy,
 * including its unique identifier, name, type (compound or not), and address.
 */
@Data
public class HospitalPharmacyDTO {
    private Long id;
    private String name;
    private boolean isCompoundPharmacy;
    private String address;
}
