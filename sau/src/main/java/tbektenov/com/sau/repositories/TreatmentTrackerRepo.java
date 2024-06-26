package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.TreatmentTracker;

/**
 * Repository interface for TreatmentSchedule entity.
 *
 * This interface extends JpaRepository to provide CRUD operations
 * for TreatmentSchedule entities, allowing interaction with the database.
 */
public interface TreatmentTrackerRepo
    extends JpaRepository<TreatmentTracker, Long> {
}
