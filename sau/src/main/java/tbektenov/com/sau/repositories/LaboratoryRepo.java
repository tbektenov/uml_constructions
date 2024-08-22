package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Laboratory;

import java.util.List;

/**
 * Repository interface for {@link Laboratory} entities.
 * Provides CRUD operations for managing laboratory entities in the database.
 *
 * <p>This interface extends {@link JpaRepository}, which includes methods for
 * basic CRUD operations, pagination, and sorting.</p>
 *
 * @see JpaRepository
 * @see Laboratory
 */
public interface LaboratoryRepo extends JpaRepository<Laboratory, Long> {

    /**
     * Finds all laboratories associated with the specified hospital ID.
     *
     * @param hospitalId the ID of the hospital
     * @return a list of laboratories associated with the specified hospital ID
     */
    List<Laboratory> findByHospitalId(Long hospitalId);
    Boolean existsByHospitalIdAndFloor(Long hospitalId, int floor);
}
