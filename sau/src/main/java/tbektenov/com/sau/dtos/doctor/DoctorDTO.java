package tbektenov.com.sau.dtos.doctor;

import lombok.Data;
import tbektenov.com.sau.dtos.hospital.HospitalDoctorCompDTO;
import tbektenov.com.sau.dtos.user.UserDTO;
import tbektenov.com.sau.models.user.userRoles.Specialization;

/**
 * Data Transfer Object (DTO) for Doctor.
 *
 * This DTO represents the detailed information of a doctor,
 * including their unique identifier, specialization, and associated user details.
 */
@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String surname;
    private Specialization specialization;
    private Long hospitalId;
}
