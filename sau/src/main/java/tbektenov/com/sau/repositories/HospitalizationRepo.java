package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.Hospitalization;

/**
 * Repository interface for Hospitalization entity.
 *
 * This interface extends JpaRepository to provide CRUD operations
 * for Hospitalization entities, with methods to interact with the database.
 */
public interface HospitalizationRepo
    extends JpaRepository<Hospitalization, Long> {
}
