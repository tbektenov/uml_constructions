package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.HospitalWard;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing HospitalWard entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations for HospitalWard entities.
 * Includes custom methods for finding wards by hospital ID and checking existence by ward number and hospital.</p>
 *
 * @see JpaRepository
 * @see HospitalWard
 */
public interface HospitalWardRepo
    extends JpaRepository<HospitalWard, Long> {

    @Override
    @EntityGraph(value = "HospitalWard.hospitalizations", type = EntityGraph.EntityGraphType.FETCH)
    Optional<HospitalWard> findById(Long aLong);

    @EntityGraph(value = "HospitalWard.hospitalizations", type = EntityGraph.EntityGraphType.FETCH)
    Optional<HospitalWard> findByWardNumAndHospitalId(String wardNum, Long hospitalId);

    /**
     * Finds all hospital wards associated with a specific hospital ID.
     *
     * @param hospitalId the ID of the hospital
     * @return a list of hospital wards associated with the specified hospital
     */
    List<HospitalWard> findByHospitalId(Long hospitalId);

    /**
     * Checks if a ward exists by its number and hospital.
     *
     * @param wardNum the ward number
     * @param hospital the hospital entity
     * @return true if a ward with the specified number exists in the given hospital, false otherwise
     */
    Boolean existsByWardNumAndHospital(String wardNum, Hospital hospital);
}
