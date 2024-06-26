package tbektenov.com.sau.dtos.hospital;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for paginated responses of Hospital.
 *
 * This DTO encapsulates the paginated response details for a list of hospitals,
 * including the hospitals' data and pagination metadata.
 */
@Data
public class HospitalResponse {
    private List<HospitalDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
