package tbektenov.com.sau.dtos.hospital;

import lombok.Data;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;

import java.util.List;

/**
 * A simple DTO for representing a hospital and its doctors.
 *
 * Contains:
 * - {@code id}: The ID of the hospital.
 * - {@code doctors}: A list of doctors working at the hospital.
 */
@Data
public class HospitalAndDoctorsDTO {
    private Long id;
    private List<DoctorDTO> doctors;
}
