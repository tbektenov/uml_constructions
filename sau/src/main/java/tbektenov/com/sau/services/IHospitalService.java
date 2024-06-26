package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.hospital.*;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyDTO;

import javax.print.Doc;
import java.util.List;

/**
 * Service interface for managing hospitals.
 * Provides methods for creating, retrieving, updating, and deleting hospitals.
 */
public interface IHospitalService {

    /**
     * Creates a new hospital.
     *
     * @param createUpdateHospitalDTO the data transfer object containing hospital details
     * @return the created {@link HospitalDTO}
     */
    HospitalDTO createHospital(CreateUpdateHospitalDTO createUpdateHospitalDTO);

    /**
     * Retrieves a paginated list of all hospitals.
     *
     * @param pageNo   the page number to retrieve
     * @param pageSize the number of records per page
     * @return a {@link HospitalResponse} containing the list of hospitals and pagination details
     */
    HospitalResponse getAllHospitals(int pageNo, int pageSize);
    List<HospitalAndDoctorsDTO> getAllHospitalsWithDoctors();

    /**
     * Retrieves a hospital by its ID.
     *
     * @param id the ID of the hospital
     * @return the retrieved {@link HospitalDTO}
     */
    HospitalDTO getHospitalById(Long id);

    /**
     * Updates an existing hospital.
     *
     * @param createUpdateHospitalDTO the data transfer object containing updated hospital details
     * @param id                      the ID of the hospital to update
     * @return the updated {@link HospitalDTO}
     */
    HospitalDTO updateHospital(CreateUpdateHospitalDTO createUpdateHospitalDTO, Long id);
    /**
     * Deletes a hospital by its ID.
     *
     * @param id the ID of the hospital to delete
     */
    void deleteHospital(Long id);
    /**
     * Adds a PrivatePharmacy as a partner to a Hospital.
     *
     * <p>This method establishes a partnership between a specified hospital and a private pharmacy by adding
     * the pharmacy to the hospital's list of partner pharmacies. It ensures that the relationship is correctly
     * reflected in the database by updating the join table `pharmacy_hospital_partners`.</p>
     *
     * <p>If the hospital or pharmacy does not exist, or if the pharmacy is already a partner, appropriate exceptions are thrown.</p>
     *
     * @param hospitalId the ID of the hospital to which the pharmacy will be added as a partner
     * @param pharmacyId the ID of the private pharmacy that will be added as a partner to the hospital
     * @return a {@link HospitalDTO} representing the updated hospital with the new partner pharmacy included
     * @throws ObjectNotFoundException if the hospital or pharmacy with the specified ID does not exist
     * @throws InvalidArgumentsException if the pharmacy is already a partner of the hospital
     */
    HospitalDTO addPartnerPharmacy(Long hospitalId, Long pharmacyId);
    /**
     * Removes a PrivatePharmacy from the list of partners of a Hospital.
     *
     * <p>This method disassociates a specified private pharmacy from a hospital by removing
     * the pharmacy from the hospital's list of partner pharmacies. It ensures that the relationship
     * is correctly reflected in the database by updating the join table `pharmacy_hospital_partners`.</p>
     *
     * <p>If the hospital or pharmacy does not exist, or if the pharmacy is not currently a partner, appropriate exceptions are thrown.</p>
     *
     * @param hospitalId the ID of the hospital from which the pharmacy will be removed as a partner
     * @param pharmacyId the ID of the private pharmacy that will be removed as a partner from the hospital
     * @return a {@link HospitalDTO} representing the updated hospital without the former partner pharmacy
     * @throws ObjectNotFoundException if the hospital or pharmacy with the specified ID does not exist
     * @throws InvalidArgumentsException if the pharmacy is not currently a partner of the hospital
     */
    HospitalDTO removePartnerPharmacy(Long hospitalId, Long pharmacyId);
    /**
     * Associates a doctor with a hospital, effectively "hiring" the doctor.
     *
     * This method links a doctor to a hospital based on their IDs, indicating that the doctor
     * will work at the specified hospital.
     *
     * @param hospitalId The unique identifier of the hospital where the doctor will be hired.
     * @param doctorId The unique identifier of the doctor to be hired.
     * @return The updated HospitalDTO reflecting the doctor being hired.
     */
    HospitalDTO hireDoctor(Long hospitalId, Long doctorId);
    /**
     * Disassociates a doctor from a hospital, effectively "firing" the doctor.
     *
     * This method removes the link between a doctor and a hospital based on their IDs,
     * indicating that the doctor will no longer work at the specified hospital.
     *
     * @param hospitalId The unique identifier of the hospital from which the doctor will be fired.
     * @param doctorId The unique identifier of the doctor to be fired.
     * @return The updated HospitalDTO reflecting the doctor being fired.
     */
    HospitalDTO fireDoctor(Long hospitalId, Long doctorId);


}
