package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;

/**
 * Repository interface for managing LeftPatient entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations for LeftPatient entities.</p>
 *
 * @see JpaRepository
 * @see LeftPatient
 */
public interface LeftPatientRepo
    extends JpaRepository<LeftPatient, Long> {
}
