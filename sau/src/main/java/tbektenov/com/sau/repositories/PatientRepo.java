package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tbektenov.com.sau.models.user.userRoles.Patient;

import java.util.Optional;

/**
 * Repository interface for {@link Patient} entities.
 * <p>
 * Provides basic CRUD operations and a method to check if a patient exists by user ID.
 * </p>
 */
public interface PatientRepo extends JpaRepository<Patient, Long> {

    @EntityGraph(value = "Patient.details", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Patient> findById(Long patientId);
}
