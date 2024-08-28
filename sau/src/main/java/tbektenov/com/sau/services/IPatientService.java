package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.left_patient.ChangeToLeftPatientDTO;
import tbektenov.com.sau.dtos.staying_patient.ChangeToStayingPatientDTO;

/**
 * Service interface for managing patient-related operations.
 *
 * This interface defines methods for retrieving, creating, updating,
 * and deleting patients, as well as handling their appointments.
 */
public interface IPatientService {
    String changeToStayingPatient(Long id, ChangeToStayingPatientDTO changeToStayingPatientDTO);
    String changeToLeftPatient(Long id, ChangeToLeftPatientDTO changeToLeftPatientDTO);
}
