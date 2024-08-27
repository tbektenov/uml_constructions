package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;

import java.util.List;

/**
 * Repository interface for managing {@link HospitalPharmacy} entities.
 *
 * <p>Provides CRUD operations and custom queries for hospital pharmacies.</p>
 *
 * @see JpaRepository
 * @see HospitalPharmacy
 */
public interface HospitalPharmacyRepo extends JpaRepository<HospitalPharmacy, Long> {

    /**
     * Retrieves all hospital pharmacies associated with a specific hospital ID.
     *
     * @param hospitalId the ID of the hospital
     * @return a list of hospital pharmacies linked to the hospital
     */
    List<HospitalPharmacy> findByHospitalId(Long hospitalId);

    /**
     * Checks if a hospital pharmacy with a specific name exists for a given hospital ID.
     *
     * @param hospitalId the ID of the hospital
     * @param name the name of the pharmacy
     * @return true if the pharmacy exists, false otherwise
     */
    Boolean existsByHospitalIdAndName(Long hospitalId, String name);
}
