package tbektenov.com.sau.dtos.pharmacy.privatePharmacy;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for paginated responses of Private Pharmacy.
 *
 * This DTO encapsulates the paginated response details for a list of private pharmacies,
 * including the pharmacy data and pagination metadata.
 */
@Data
public class PrivatePharmacyResponse {
    private List<PrivatePharmacyDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
