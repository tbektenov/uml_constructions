package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.laboratory.CreateUpdateLaboratoryDTO;
import tbektenov.com.sau.dtos.laboratory.LaboratoryDTO;

import java.util.List;

/**
 * Service interface for managing laboratory entities.
 *
 * <p>This interface defines methods for creating, retrieving, updating, and deleting laboratory entities
 * within a specified hospital. It provides a contract for the implementation classes to handle the business
 * logic related to laboratory management.</p>
 */
public interface ILaboratoryService {

    /**
     * Creates a new laboratory for a specified hospital.
     *
     * @param hospitalId                the ID of the hospital to which the laboratory will be added
     * @param createUpdateLaboratoryDTO the data transfer object containing the details of the laboratory to be created
     * @return the created {@link LaboratoryDTO}
     */
    LaboratoryDTO createLaboratory(Long hospitalId, CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO);

    /**
     * Retrieves a list of all laboratories associated with a specified hospital.
     *
     * @param hospitalId the ID of the hospital whose laboratories are to be retrieved
     * @return a list of {@link LaboratoryDTO} objects representing the laboratories of the specified hospital
     */
    List<LaboratoryDTO> getLaboratoriesByHospitalId(Long hospitalId);

    /**
     * Retrieves a laboratory by its ID and associated hospital ID.
     *
     * @param hospitalId    the ID of the hospital to which the laboratory belongs
     * @param laboratoryId  the ID of the laboratory to be retrieved
     * @return the {@link LaboratoryDTO} object representing the requested laboratory
     */
    LaboratoryDTO getLaboratoryById(Long hospitalId, Long laboratoryId);

    /**
     * Updates an existing laboratory for a specified hospital.
     *
     * @param hospitalId                the ID of the hospital to which the laboratory belongs
     * @param laboratoryId              the ID of the laboratory to be updated
     * @param createUpdateLaboratoryDTO the data transfer object containing the updated details of the laboratory
     * @return the updated {@link LaboratoryDTO}
     */
    LaboratoryDTO updateLaboratory(Long hospitalId, Long laboratoryId, CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO);
    /**
     * Deletes a laboratory from database including all associations.
     *
     * @param hospitalId   the ID of the hospital to which the laboratory belongs
     * @param laboratoryId the ID of the laboratory to be deleted
     */
    void deleteLaboratory(Long hospitalId, Long laboratoryId);
}
