package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.CreateUpdatePrivatePharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyResponse;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.List;

/**
 * Service interface for managing private pharmacy operations.
 *
 * This interface defines methods for creating, retrieving, updating,
 * and deleting private pharmacies, as well as managing their association
 * with partner hospitals.
 */
public interface IPrivatePharmacyService {

    /**
     * Creates a new private pharmacy with the specified details.
     *
     * @param createUpdatePrivatePharmacyDTO The data transfer object containing details for the new private pharmacy.
     * @return The created PrivatePharmacyDTO with the pharmacy's details.
     */
    PrivatePharmacyDTO createPrivatePharmacy(CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO);

    /**
     * Retrieves a paginated list of all private pharmacies.
     *
     * @param pageNo The page number to retrieve.
     * @param pageSize The number of pharmacies per page.
     * @return A PrivatePharmacyResponse containing the paginated list of private pharmacies.
     */
    PrivatePharmacyResponse getAllPrivatePharmacies(int pageNo, int pageSize);

    /**
     * Retrieves a private pharmacy by its unique ID.
     *
     * @param privatePharmacyId The unique identifier of the private pharmacy.
     * @return The PrivatePharmacyDTO with the pharmacy's details.
     */
    PrivatePharmacyDTO getPrivatePharmacyById(Long privatePharmacyId);

    /**
     * Updates an existing private pharmacy's details by its unique ID.
     *
     * @param createUpdatePrivatePharmacyDTO The data transfer object containing the updated details for the private pharmacy.
     * @param privatePharmacyId The unique identifier of the private pharmacy to update.
     * @return The updated PrivatePharmacyDTO with the new pharmacy's details.
     */
    PrivatePharmacyDTO updatePrivatePharmacy(CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO, Long privatePharmacyId);

    /**
     * Deletes a private pharmacy identified by its unique ID.
     *
     * @param privatePharmacyId The unique identifier of the private pharmacy to be deleted.
     */
    void deletePrivatePharmacy(Long privatePharmacyId);
    /**
     * Adds a hospital as a partner to the private pharmacy.
     *
     * @param pharmacyId The unique identifier of the private pharmacy.
     * @param hospitalId The unique identifier of the hospital to be added as a partner.
     * @return The updated PrivatePharmacyDTO with the new partner hospital details.
     */
    PrivatePharmacyDTO addPartnerHospital(Long pharmacyId, Long hospitalId);
    /**
     * Removes a hospital from being a partner to the private pharmacy.
     *
     * @param pharmacyId The unique identifier of the private pharmacy.
     * @param hospitalId The unique identifier of the hospital to be removed as a partner.
     * @return The updated PrivatePharmacyDTO without the removed partner hospital.
     */
    PrivatePharmacyDTO removePartnerHospital(Long pharmacyId, Long hospitalId);
}
