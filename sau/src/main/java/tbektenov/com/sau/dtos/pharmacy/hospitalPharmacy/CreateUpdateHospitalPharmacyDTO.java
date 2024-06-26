package tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for creating or updating a hospital pharmacy.
 *
 * This DTO contains the necessary details to create a new hospital pharmacy or update an existing one,
 * including the name of the pharmacy and whether it is a compound pharmacy.
 */
@Data
public class CreateUpdateHospitalPharmacyDTO {
    private String name;
    private boolean isCompoundPharmacy = false;
}