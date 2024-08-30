package tbektenov.com.sau.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.Optional;

/**
 * Repository interface for managing {@link Hospital} entities.
 *
 * <p>Provides CRUD operations and additional queries for interacting with the database.</p>
 *
 * @see JpaRepository
 * @see Hospital
 */
public interface HospitalRepo
        extends JpaRepository<Hospital, Long> {

    /**
     * Retrieves a paginated list of hospitals with their associated pharmacies and doctors.
     *
     * @param pageable pagination details
     * @return a paginated list of hospitals
     */
    @EntityGraph(value = "Hospital.details", type = EntityGraph.EntityGraphType.FETCH)
    Page<Hospital> findAll(Pageable pageable);

    /**
     * Checks if a hospital exists by its name.
     *
     * @param name the name of the hospital
     * @return true if a hospital with the given name exists, false otherwise
     */
    Boolean existsByName(String name);

    /**
     * Finds a hospital by its name.
     *
     * @param name the name of the hospital
     * @return an optional containing the hospital if found, or empty if not
     */
    @EntityGraph(value = "Hospital.details", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Hospital> findByName(String name);

    /**
     * Retrieves a Hospital by its ID, using an entity graph to fetch related details.
     *
     * The entity graph "Hospital.details" is used to optimize the fetch of related
     * entities, such as associated wards, doctors, laboratories, etc.
     *
     * @param hospitalId The ID of the hospital.
     * @return An Optional containing the Hospital if found, otherwise empty.
     */
    @Override
    @EntityGraph(value = "Hospital.details", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Hospital> findById(Long hospitalId);
}
