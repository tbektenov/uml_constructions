package tbektenov.com.sau.dtos.staying_patient;

import lombok.Data;

/**
 * DTO for changing a patient to a staying patient.
 *
 * <p>Includes hospital ID, nurse ID, and ward number.</p>
 */
@Data
public class ChangeToStayingPatientDTO {
    private Long hospitalId;
    private Long nurseId;
    private String wardNum;
}
