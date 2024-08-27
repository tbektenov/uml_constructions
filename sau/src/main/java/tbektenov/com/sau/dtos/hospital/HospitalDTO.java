package tbektenov.com.sau.dtos.hospital;

import lombok.Data;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;

import java.util.Set;

/**
 * DTO representing a hospital.
 *
 * Fields:
 * - {@code hospitalId}: The unique identifier of the hospital.
 * - {@code name}: The name of the hospital.
 * - {@code address}: The address of the hospital.
 * - {@code doctors}: A set of associated doctors.
 */
@Data
public class HospitalDTO {
    private Long hospitalId;
    private String name;
    private String address;
    private Set<DoctorDTO> doctors;
}
