package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.left_patient.ChangeToLeftPatientDTO;
import tbektenov.com.sau.dtos.staying_patient.ChangeToStayingPatientDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;

/**
 * Service interface for managing patient-related operations.
 *
 * This interface defines methods for retrieving, creating, updating,
 * and deleting patients, as well as handling their appointments.
 */
public interface IPatientService {
    /**
     * Changes the status of a patient to "staying".
     *
     * @param patientId                 The ID of the patient to update.
     * @param changeToStayingPatientDTO DTO containing details for the transition to staying patient.
     * @throws ObjectNotFoundException if the patient, nurse, or ward cannot be found.
     */
    void changeToStayingPatient(Long patientId, ChangeToStayingPatientDTO changeToStayingPatientDTO);

    /**
     * Changes the status of a patient to "left".
     *
     * @param patientId              The ID of the patient to update.
     * @param changeToLeftPatientDTO DTO containing details for the transition to left patient.
     * @throws ObjectNotFoundException if the patient cannot be found or if the conclusion is missing.
     */
    void changeToLeftPatient(Long patientId, ChangeToLeftPatientDTO changeToLeftPatientDTO);
}
