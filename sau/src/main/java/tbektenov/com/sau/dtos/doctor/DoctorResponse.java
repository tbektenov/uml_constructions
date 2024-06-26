package tbektenov.com.sau.dtos.doctor;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for paginated responses of Doctor.
 *
 * This DTO encapsulates the paginated response details for a list of doctors,
 * including the doctors' data and pagination metadata.
 */
@Data
public class DoctorResponse {
    private List<DoctorDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
