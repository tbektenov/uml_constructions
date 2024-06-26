package tbektenov.com.sau.dtos.hospital;

import lombok.Data;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;

import java.util.List;

@Data
public class HospitalAndDoctorsDTO {
    private Long id;
    private List<DoctorDTO> doctors;
}
