package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;

import java.util.List;

/**
 * Repository interface for {@link HospitalPharmacy} entities.
 * Provides CRUD operations for managing hospital pharmacy entities in the database.
 *
 * <p>This interface extends {@link JpaRepository}, which includes methods for
 * basic CRUD operations, pagination, and sorting. It defines custom repository operations specific
 * to {@link HospitalPharmacy} entities, facilitating access to pharmacy data related to specific hospitals.</p>
 *
 * @see JpaRepository
 * @see HospitalPharmacy
 */
public interface HospitalPharmacyRepo extends JpaRepository<HospitalPharmacy, Long> {

    /**
     * Finds all hospital pharmacies associated with a specific hospital ID.
     * This method facilitates the retrieval of all pharmacies that are linked to a particular hospital,
     * supporting the management of hospital pharmacy resources within the healthcare system.
     *
     * @param hospitalId the ID of the hospital whose pharmacies are to be retrieved
     * @return a list of {@link HospitalPharmacy} entities associated with the specified hospital ID
     */
    List<HospitalPharmacy> findByHospitalId(Long hospitalId);
    Boolean existsByHospitalIdAndName(Long hospitalId, String name);
}
