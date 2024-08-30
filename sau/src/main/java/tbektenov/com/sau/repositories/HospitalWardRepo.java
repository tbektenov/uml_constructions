package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.HospitalWard;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing HospitalWard entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations for HospitalWard entities.
 * Includes custom methods for finding wards by hospital ID and checking existence by ward number and hospital.</p>
 *
 * @see JpaRepository
 * @see HospitalWard
 */
public interface HospitalWardRepo
    extends JpaRepository<HospitalWard, Long> {

    /**
     * Retrieves a HospitalWard by its ID, using an entity graph to fetch related hospitalizations.
     *
     * The entity graph "HospitalWard.hospitalizations" is used to optimize the fetch of related
     * hospitalizations associated with the ward.
     *
     * @param aLong The ID of the HospitalWard.
     * @return An Optional containing the HospitalWard if found, otherwise empty.
     */
    @Override
    @EntityGraph(value = "HospitalWard.hospitalizations", type = EntityGraph.EntityGraphType.FETCH)
    Optional<HospitalWard> findById(Long aLong);

    /**
     * Retrieves a HospitalWard by its ward number and hospital ID, using an entity graph to fetch related hospitalizations.
     *
     * The entity graph "HospitalWard.hospitalizations" is used to optimize the fetch of related
     * hospitalizations associated with the ward.
     *
     * @param wardNum The ward number of the HospitalWard.
     * @param hospitalId The ID of the hospital.
     * @return An Optional containing the HospitalWard if found, otherwise empty.
     */
    @EntityGraph(value = "HospitalWard.hospitalizations", type = EntityGraph.EntityGraphType.FETCH)
    Optional<HospitalWard> findByWardNumAndHospitalId(String wardNum, Long hospitalId);

    /**
     * Checks if a ward exists by its number and hospital.
     *
     * @param wardNum the ward number
     * @param hospital the hospital entity
     * @return true if a ward with the specified number exists in the given hospital, false otherwise
     */
    Boolean existsByWardNumAndHospital(String wardNum, Hospital hospital);
}
