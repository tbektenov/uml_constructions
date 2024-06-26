package tbektenov.com.sau.dtos.pharmacy.privatePharmacy;

import lombok.Data;
import tbektenov.com.sau.dtos.hospital.HospitalDTO;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Private Pharmacy.
 *
 * This DTO represents the detailed information of a private pharmacy,
 * including its unique identifier, name, type (compound or not), address,
 * associated pharmaceutical company, and partner hospitals.
 */
@Data
public class PrivatePharmacyDTO {
    private Long id;
    private String name;
    private boolean isCompoundPharmacy;
    private String address;
    private String pharmaCompany;
    private List<HospitalDTO> partnerHospitals;
}
