package tbektenov.com.sau.dtos.staying_patient;

import lombok.Data;

@Data
public class ChangeToStayingPatientDTO {
    Long hospitalId;
    Long nurseId;
    String wardNum;
}
