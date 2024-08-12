package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;
import tbektenov.com.sau.dtos.patient.CreatePatientDTO;
import tbektenov.com.sau.dtos.patient.PatientDTO;
import tbektenov.com.sau.dtos.patient.UpdatePatientDTO;

import java.util.List;

/**
 * Service interface for managing patient-related operations.
 *
 * This interface defines methods for retrieving, creating, updating,
 * and deleting patients, as well as handling their appointments.
 */
public interface IPatientService {
    /**
     * Retrieves a list of all patients.
     *
     * @return A list of PatientDTO objects representing all patients.
     */
    List<PatientDTO> getAllPatients();
    /**
     * Creates a new patient with the specified details.
     *
     * @param createPatientDTO The data transfer object containing details for the new patient.
     * @return The created PatientDTO with the patient's details.
     */
    PatientDTO createPatient(CreatePatientDTO createPatientDTO);
    /**
     * Retrieves a patient by their unique ID.
     *
     * @param id The unique identifier of the patient.
     * @return The PatientDTO with the patient's details.
     */
    PatientDTO getPatientById(Long id);
    /**
     * Updates an existing patient's details by their unique ID.
     *
     * @param updatePatientDTO The data transfer object containing the updated details for the patient.
     * @param id The unique identifier of the patient to update.
     * @return The updated PatientDTO with the new patient's details.
     */
    PatientDTO updatePatient(UpdatePatientDTO updatePatientDTO, Long id);
    /**
     * Deletes a patient identified by their unique ID.
     *
     * @param id The unique identifier of the patient to be deleted.
     */
    void deletePatient(Long id);
    /**
     * Adds an appointment for a patient based on the provided details.
     *
     * @param createAppointmentDTO The data transfer object containing details for the new appointment.
     * @return The updated PatientDTO with the new appointment added.
     */
    PatientDTO addAppointment(CreateAppointmentDTO createAppointmentDTO);
    /**
     * Updates an existing appointment for a patient.
     *
     * @param createAppointmentDTO The data transfer object containing updated details for the appointment.
     * @param id The unique identifier of the appointment to update.
     * @return The updated PatientDTO with the modified appointment details.
     */
    PatientDTO updateAppointment(CreateAppointmentDTO createAppointmentDTO, Long id);
    /**
     * Deletes an appointment identified by its unique ID.
     *
     * @param id The unique identifier of the appointment to be deleted.
     * @return The updated PatientDTO after the appointment is deleted.
     */
    PatientDTO cancelAppointment(Long id);

    String changeToStayingPatient(Long id, String wardNum);
    String changeToLeftPatient(Long id, String conclusion);
}
