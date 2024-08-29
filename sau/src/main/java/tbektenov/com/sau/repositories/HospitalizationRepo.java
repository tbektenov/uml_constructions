package tbektenov.com.sau.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.Hospitalization;

import java.util.Optional;

/**
 * Repository interface for managing {@link Hospitalization} entities.
 * Extends {@link JpaRepository} to provide CRUD operations for the entity.
 */
public interface HospitalizationRepo extends JpaRepository<Hospitalization, Long> {

    /**
     * Finds a {@link Hospitalization} entity by its ID with additional details loaded.
     * Uses the "Hospitalization.details" entity graph to load associated entities in the same query.
     *
     * @param hospitalizationId the ID of the hospitalization to retrieve.
     * @return an {@link Optional} containing the found {@link Hospitalization} or empty if not found.
     */
    @Override
    @EntityGraph(value = "Hospitalization.details", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Hospitalization> findById(@NonNull Long hospitalizationId);
}
