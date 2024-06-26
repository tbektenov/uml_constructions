package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.nurse.CreateNurseDTO;
import tbektenov.com.sau.dtos.nurse.NurseDTO;
import tbektenov.com.sau.dtos.nurse.UpdateNurseDTO;

import java.util.List;

/**
 * Service interface for managing nurse-related operations.
 *
 * This interface defines methods for retrieving, creating, updating,
 * and deleting nurses, as well as fetching all nurses.
 */
public interface INurseService {
    /**
     * Retrieves a list of all nurses.
     *
     * @return A list of NurseDTO objects representing all nurses.
     */
    List<NurseDTO> getAllNurses();

    /**
     * Creates a new nurse with the specified details.
     *
     * @param createNurseDTO The data transfer object containing details for the new nurse.
     * @return The created NurseDTO with the nurse's details.
     */
    NurseDTO createNurse(CreateNurseDTO createNurseDTO);

    /**
     * Retrieves a nurse by their unique ID.
     *
     * @param id The unique identifier of the nurse.
     * @return The NurseDTO with the nurse's details.
     */
    NurseDTO getNurseById(Long id);

    /**
     * Updates an existing nurse's details by their unique ID.
     *
     * @param updateNurseDTO The data transfer object containing the updated details for the nurse.
     * @param id The unique identifier of the nurse to update.
     * @return The updated NurseDTO with the new nurse's details.
     */
    NurseDTO updateNurse(UpdateNurseDTO updateNurseDTO, Long id);

    /**
     * Deletes a nurse identified by their unique ID.
     *
     * @param id The unique identifier of the nurse to be deleted.
     */
    void deleteNurse(Long id);
}
