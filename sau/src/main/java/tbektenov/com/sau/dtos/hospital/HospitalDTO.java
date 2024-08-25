package tbektenov.com.sau.dtos.hospital;

import lombok.Data;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyDTO;

import java.util.Set;

/**
 * Data Transfer Object for representing a hospital.
 * This class encapsulates the necessary information
 * about a hospital entity.
 *
 * <p>Uses Lombok's {@code @Data} annotation to automatically generate
 * getter, setter, toString, equals, and hashCode methods.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code id}: The unique identifier of the hospital.</li>
 *   <li>{@code name}: The name of the hospital.</li>
 *   <li>{@code address}: The address of the hospital.</li>
 * </ul>
 *
 * <p>Example:</p>
 * <pre>
 *     HospitalDTO hospitalDTO = new HospitalDTO();
 *     hospitalDTO.setId(1);
 *     hospitalDTO.setName("City Hospital");
 *     hospitalDTO.setAddress("123 Main St, Anytown, USA");
 * </pre>
 *
 * @see lombok.Data
 */
@Data
public class HospitalDTO {
    private Long hospitalId;
    private String name;
    private String address;
    private Set<PrivatePharmacyDTO> partnerPharmacies;
    private Set<DoctorDTO> doctors;
}
