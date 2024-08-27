package tbektenov.com.sau.dtos.hospital;

import lombok.Data;

import java.util.List;

/**
 * DTO for paginated responses of hospitals.
 *
 * Fields:
 * - {@code content}: The list of hospital data.
 * - {@code pageNo}: The current page number.
 * - {@code pageSize}: The number of items per page.
 * - {@code totalElements}: The total number of elements.
 * - {@code totalPages}: The total number of pages.
 * - {@code last}: Indicates if this is the last page.
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
