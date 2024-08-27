package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.userRoles.Patient;

import java.util.Optional;

/**
        * Repository interface for {@link Patient} entities.
 *
         * <p>Provides basic CRUD operations and a method to find a patient by ID with associated details.</p>
        *
        * @see JpaRepository
 * @see Patient
 */
public interface PatientRepo extends JpaRepository<Patient, Long> {

    /**
     * Finds a {@link Patient} by its ID and fetches associated details, including appointments,
     * left patient status, and staying patient status.
     *
     * <p>This method uses an entity graph to load the associated details of the patient
     * to avoid lazy loading issues.</p>
     *
     * @param patientId the ID of the patient to find
     * @return an {@link Optional} containing the patient if found, or empty if not
     */
    @EntityGraph(value = "Patient.details", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Patient> findById(Long patientId);
}
