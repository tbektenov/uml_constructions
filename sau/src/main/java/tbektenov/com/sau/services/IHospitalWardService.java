package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.hospitalWard.CreateUpdateHospitalWardDTO;
import tbektenov.com.sau.dtos.hospitalWard.HospitalWardDTO;

import java.util.List;

/**
 * Service interface for managing hospital wards.
 *
 * This interface defines methods for creating, retrieving, updating, and deleting hospital wards,
 * as well as handling their association with specific hospitals.
 */
public interface IHospitalWardService {
    /**
     * Creates a new hospital ward associated with a specific hospital.
     *
     * @param hospitalId The unique identifier of the hospital to which the ward will be added.
     * @param createUpdateHospitalWardDTO The data transfer object containing details for the new hospital ward.
     * @return The created HospitalWardDTO with the hospital ward's details.
     */
    HospitalWardDTO createHospitalWard(Long hospitalId, CreateUpdateHospitalWardDTO createUpdateHospitalWardDTO);

    /**
     * Retrieves a list of hospital wards associated with a specific hospital.
     *
     * @param hospitalId The unique identifier of the hospital.
     * @return A list of HospitalWardDTO objects representing wards in the specified hospital.
     */
    List<HospitalWardDTO> getHospitalWardsByHospitalId(Long hospitalId);

    /**
     * Retrieves details of a specific hospital ward by its ID within a specific hospital.
     *
     * @param hospitalId The unique identifier of the hospital.
     * @param hospitalWardId The unique identifier of the hospital ward.
     * @return The HospitalWardDTO with the details of the specified hospital ward.
     */
    HospitalWardDTO getHospitalWardById(Long hospitalId, Long hospitalWardId);

    /**
     * Updates details of a specific hospital ward by its ID within a specific hospital.
     *
     * @param hospitalId The unique identifier of the hospital.
     * @param hospitalWardId The unique identifier of the hospital ward to be updated.
     * @param createUpdateHospitalWardDTO The data transfer object containing updated details for the hospital ward.
     * @return The updated HospitalWardDTO with the new details of the hospital ward.
     */
    HospitalWardDTO updateHospitalWard(Long hospitalId, Long hospitalWardId, CreateUpdateHospitalWardDTO createUpdateHospitalWardDTO);

    /**
     * Deletes a specific hospital ward by its ID within a specific hospital.
     *
     * @param hospitalId The unique identifier of the hospital.
     * @param hospitalWardId The unique identifier of the hospital ward to be deleted.
     */
    void deleteHospitalWard(Long hospitalId, Long hospitalWardId);
}
