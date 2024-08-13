package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import java.util.List;

/**
 * Repository interface for managing {@link Doctor} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing basic CRUD operations and additional
 * methods to manage {@link Doctor} entities in the database.
 * </p>
 *
 * <pre>
 * Example usage:
 * {@code
 * @Autowired
 * private IDoctorRepo doctorRepo;
 *
 * public void someMethod() {
 *     // Check if a doctor exists by user ID
 *     boolean exists = doctorRepo.existsByUserId(1);
 * }
 * }
 * </pre>
 *
 * @see JpaRepository
 * @see Doctor
 */
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    /**
     * Finds all doctors associated with the specified hospital ID.
     *
     * @param hospitalId the ID of the hospital
     * @return a list of doctors associated with the specified hospital
     */
    @EntityGraph(value = "Doctor.detailsHospitalAndLaboratory", type = EntityGraph.EntityGraphType.LOAD)
    List<Doctor> findByHospitalId(Long hospitalId);
}
