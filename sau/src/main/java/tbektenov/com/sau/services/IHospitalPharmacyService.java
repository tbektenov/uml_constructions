package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.CreateUpdateHospitalPharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.HospitalPharmacyDTO;

import java.util.List;

/**
 * Interface defining the service layer operations for managing hospital pharmacies.
 * This includes creation, retrieval, update, and deletion of hospital pharmacy entries linked to specific hospitals.
 * The methods ensure proper validation against hospital existence and manage data transformations between entities and DTOs.
 */
public interface IHospitalPharmacyService {

    /**
     * Creates a new hospital pharmacy and links it to a specific hospital.
     *
     * @param hospitalId The ID of the hospital to which the pharmacy will be linked.
     * @param createUpdateHospitalPharmacyDTO The DTO containing all necessary data to create a new hospital pharmacy.
     * @return HospitalPharmacyDTO The DTO representation of the newly created hospital pharmacy.
     */
    HospitalPharmacyDTO createHospitalPharmacy(Long hospitalId, CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO);

    /**
     * Retrieves all hospital pharmacies associated with a specific hospital.
     *
     * @param hospitalId The ID of the hospital whose pharmacies are to be retrieved.
     * @return List of HospitalPharmacyDTO representing all pharmacies associated with the specified hospital.
     */
    List<HospitalPharmacyDTO> getHospitalPharmaciesByHospitalId(Long hospitalId);

    /**
     * Retrieves a specific hospital pharmacy by its ID, ensuring it belongs to the specified hospital.
     *
     * @param hospitalId The hospital ID to which the pharmacy should belong.
     * @param hospitalPharmacyId The ID of the hospital pharmacy to retrieve.
     * @return HospitalPharmacyDTO The DTO representation of the specified hospital pharmacy.
     */
    HospitalPharmacyDTO getHospitalPharmacyById(Long hospitalId, Long hospitalPharmacyId);

    /**
     * Updates an existing hospital pharmacy with new data, ensuring it belongs to the specified hospital.
     *
     * @param hospitalId The ID of the hospital to which the pharmacy belongs.
     * @param hospitalPharmacyId The ID of the hospital pharmacy to update.
     * @param createUpdateHospitalPharmacyDTO The DTO containing the updated data for the hospital pharmacy.
     * @return HospitalPharmacyDTO The updated DTO representation of the hospital pharmacy.
     */
    HospitalPharmacyDTO updateHospitalPharmacy(Long hospitalId, Long hospitalPharmacyId, CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO);

    /**
     * Deletes a hospital pharmacy, ensuring it belongs to the specified hospital.
     *
     * @param hospitalId The ID of the hospital to which the pharmacy belongs.
     * @param hospitalPharmacyId The ID of the pharmacy to be deleted.
     */
    void deleteHospitalPharmacy(Long hospitalId, Long hospitalPharmacyId);
}

