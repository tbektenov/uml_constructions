package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Doctor} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing basic CRUD operations and additional
 * methods to manage {@link Doctor} entities in the database.
 * </p>
 *
 * @see JpaRepository
 * @see Doctor
 */
public interface DoctorRepo extends JpaRepository<Doctor, Long> {

    /**
     * Retrieves all doctors with their associated hospital and laboratory details.
     *
     * @return a list of all doctors with their hospital and laboratory details
     */
    @EntityGraph(value = "Doctor.detailsHospitalAndLaboratory", type = EntityGraph.EntityGraphType.LOAD)
    List<Doctor> findAll();

    /**
     * Retrieves a doctor by their ID, including associated hospital and laboratory details.
     *
     * @param id the ID of the doctor
     * @return an optional containing the doctor if found, or empty if not
     */
    @Override
    @EntityGraph(value = "Doctor.detailsHospitalAndLaboratory", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Doctor> findById(Long id);


}
