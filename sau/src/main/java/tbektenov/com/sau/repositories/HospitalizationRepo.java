package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.Hospitalization;

/**
 * Repository interface for managing Hospitalization entities.
 *
 * <p>Extends {@link JpaRepository} to provide basic CRUD operations
 * for the Hospitalization entity.</p>
 *
 * @see JpaRepository
 * @see Hospitalization
 */
public interface HospitalizationRepo
    extends JpaRepository<Hospitalization, Long> {
}
