package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.TreatmentTracker;

/**
 * Repository interface for {@link TreatmentTracker} entities.
 * Provides CRUD operations for managing treatment tracker records in the database.
 *
 * <p>This interface extends {@link JpaRepository}, which includes methods for
 * basic CRUD operations, pagination, and sorting.</p>
 *
 * @see JpaRepository
 * @see TreatmentTracker
 */
public interface TreatmentTrackerRepo
    extends JpaRepository<TreatmentTracker, Long> {
}
