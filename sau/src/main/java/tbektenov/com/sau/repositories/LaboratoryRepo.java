package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Laboratory;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing Laboratory entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations for Laboratory entities.
 * Includes custom methods for finding laboratories by hospital ID and checking existence by hospital ID and floor.</p>
 *
 * @see JpaRepository
 * @see Laboratory
 */
public interface LaboratoryRepo extends JpaRepository<Laboratory, Long> {

    /**
     * Checks if a laboratory exists by its hospital ID and floor.
     *
     * @param hospitalId the ID of the hospital
     * @param floor the floor of the laboratory
     * @return true if a laboratory with the specified hospital ID and floor exists, false otherwise
     */
    Boolean existsByHospitalIdAndFloor(Long hospitalId, int floor);
}
