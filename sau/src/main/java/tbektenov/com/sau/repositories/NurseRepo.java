package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.userRoles.Nurse;

import java.util.Optional;

/**
 * Repository interface for {@link Nurse} entities.
 *
 * <p>Provides basic CRUD operations and a method to find a nurse by ID with associated hospitalizations.</p>
 *
 * @see JpaRepository
 * @see Nurse
 */
public interface NurseRepo extends JpaRepository<Nurse, Long> {

    /**
     * Finds a {@link Nurse} by its ID and fetches associated hospitalizations.
     *
     * <p>This method uses an entity graph to load the associated hospitalizations
     * of the nurse to avoid lazy loading issues.</p>
     *
     * @param aLong the ID of the nurse to find
     * @return an {@link Optional} containing the nurse if found, or empty if not
     */
    @Override
    @EntityGraph(value = "Nurse.hospitalizations", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Nurse> findById(Long aLong);
}
