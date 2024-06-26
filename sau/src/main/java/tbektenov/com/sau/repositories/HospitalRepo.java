package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Hospital;

/**
 * Repository interface for {@link Hospital} entities.
 * Provides CRUD operations for managing hospital entities in the database.
 *
 * <p>This interface extends {@link JpaRepository}, which includes methods for
 * basic CRUD operations, pagination, and sorting.</p>
 *
 * @see JpaRepository
 * @see Hospital
 */
public interface HospitalRepo
        extends JpaRepository<Hospital, Long> {
}
