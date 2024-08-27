package tbektenov.com.sau.dtos.left_patient;

import lombok.Data;

/**
 * DTO for changing a patient's status to "left."
 *
 * Fields:
 * - {@code conclusion}: The conclusion or reason for the patient leaving.
 */
@Data
public class ChangeToLeftPatientDTO {
    String conclusion;
}
