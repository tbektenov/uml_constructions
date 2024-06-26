package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.userRoles.Nurse;

/**
 * Repository interface for {@link Nurse} entities.
 * <p>
 * Provides basic CRUD operations and a method to check if a nurse exists by user ID.
 * </p>
 */
public interface NurseRepo extends JpaRepository<Nurse, Long> {

}
