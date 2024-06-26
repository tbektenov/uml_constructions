package tbektenov.com.sau.dtos.pharmacy.privatePharmacy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Data Transfer Object (DTO) for creating or updating a private pharmacy.
 *
 * This DTO contains the necessary details to create a new private pharmacy or update an existing one,
 * including the name, type (compound or not), address, associated pharmaceutical company, and partner hospital IDs.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdatePrivatePharmacyDTO {
    private String name;
    private boolean isCompoundPharmacy = false;
    private String address;
    private String pharmaCompany;
    private List<Integer> hospitalIds;
}
