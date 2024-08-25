package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.userRoles.Nurse;

import java.util.Optional;

/**
 * Repository interface for {@link Nurse} entities.
 * <p>
 * Provides basic CRUD operations and a method to check if a nurse exists by user ID.
 * </p>
 */
public interface NurseRepo extends JpaRepository<Nurse, Long> {

    @Override
    @EntityGraph(value = "Nurse.hospitalizations", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Nurse> findById(Long aLong);
}
