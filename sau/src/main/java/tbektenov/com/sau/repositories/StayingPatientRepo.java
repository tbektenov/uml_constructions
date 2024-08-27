package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;

/**
 * Repository interface for {@link StayingPatient} entities.
 * Provides CRUD operations for managing staying patient entities in the database.
 *
 * <p>This interface extends {@link JpaRepository}, which includes methods for
 * basic CRUD operations, pagination, and sorting.</p>
 *
 * @see JpaRepository
 * @see StayingPatient
 */
public interface StayingPatientRepo
    extends JpaRepository<StayingPatient, Long> {
}
