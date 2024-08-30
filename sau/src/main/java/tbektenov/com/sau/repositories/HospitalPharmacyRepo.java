package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;

import java.util.Optional;

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
     * Checks if a hospital pharmacy with a specific name exists for a given hospital ID.
     *
     * @param hospitalId the ID of the hospital
     * @param name the name of the pharmacy
     * @return true if the pharmacy exists, false otherwise
     */
    Boolean existsByHospitalIdAndName(Long hospitalId, String name);

    /**
     * Retrieves a HospitalPharmacy by its name and associated hospital ID,
     * using an entity graph to fetch related order details.
     * <p>
     * The entity graph "HP.orderDetails" is used to optimize the fetch
     * of related entities.
     *
     * @param name The name of the hospital pharmacy.
     * @param hospitalId The ID of the associated hospital.
     * @return An Optional containing the HospitalPharmacy if found, otherwise empty.
     */
    @EntityGraph(value = "HP.orderDetails", type = EntityGraph.EntityGraphType.FETCH)
    Optional<HospitalPharmacy> findByNameAndHospitalId(String name, Long hospitalId);

}
