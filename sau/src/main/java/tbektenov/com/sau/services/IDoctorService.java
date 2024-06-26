package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.doctor.CreateDoctorDTO;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.dtos.doctor.DoctorResponse;
import tbektenov.com.sau.dtos.doctor.UpdateDoctorDTO;

import java.util.List;

/**
 * Service interface for handling doctor-related operations.
 *
 * This interface defines methods for managing doctors, including retrieving, creating, updating,
 * deleting, and managing their association with hospitals.
 */
public interface IDoctorService {
    /**
     * Retrieves a paginated list of all doctors.
     *
     * @param pageNo The page number to retrieve.
     * @param pageSize The number of doctors per page.
     * @return A DoctorResponse containing the paginated list of doctors.
     */
    DoctorResponse getAllDoctors(int pageNo, int pageSize);

    /**
     * Creates a new doctor with the specified details.
     *
     * @param createDoctorDTO The data transfer object containing the details for the new doctor.
     * @return The created DoctorDTO with the doctor's details.
     */
    String createDoctor(CreateDoctorDTO createDoctorDTO);

    /**
     * Retrieves a doctor by their unique ID.
     *
     * @param id The unique identifier of the doctor.
     * @return The DoctorDTO with the doctor's details.
     */
    DoctorDTO getDoctorById(Long id);

    /**
     * Updates an existing doctor's details by their unique ID.
     *
     * @param updateDoctorDTO The data transfer object containing the updated details for the doctor.
     * @param id The unique identifier of the doctor to update.
     * @return The updated DoctorDTO with the new doctor's details.
     */
    DoctorDTO updateDoctor(UpdateDoctorDTO updateDoctorDTO, Long id);

    /**
     * Deletes a doctor identified by their unique ID.
     *
     * @param id The unique identifier of the doctor to be deleted.
     */
    void deleteDoctor(Long id);

    /**
     * Retrieves a list of doctors associated with a specific hospital by the hospital's ID.
     *
     * @param id The unique identifier of the hospital.
     * @return A list of DoctorDTO objects representing doctors from the specified hospital.
     */
    List<DoctorDTO> getDoctorsFromHospitalById(Long id);

    /**
     * Associates a doctor with a hospital by their IDs.
     *
     * @param doctorId The unique identifier of the doctor.
     * @param hospitalId The unique identifier of the hospital.
     * @return The updated DoctorDTO with the association details.
     */
    DoctorDTO assignDoctorToAnotherHospital(Long doctorId, Long hospitalId);

    /**
     * Removes the association of a doctor from a hospital by their IDs.
     *
     * @param doctorId The unique identifier of the doctor.
     * @param hospitalId The unique identifier of the hospital.
     */
    void removeDoctorFromHospital(Long doctorId, Long hospitalId);
    void finishAppointment(Long doctorId, Long appointId);
}
