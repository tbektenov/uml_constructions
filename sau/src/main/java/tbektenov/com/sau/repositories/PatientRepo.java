package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.userRoles.Patient;

/**
 * Repository interface for {@link Patient} entities.
 * <p>
 * Provides basic CRUD operations and a method to check if a patient exists by user ID.
 * </p>
 */
public interface PatientRepo extends JpaRepository<Patient, Long> {

}
