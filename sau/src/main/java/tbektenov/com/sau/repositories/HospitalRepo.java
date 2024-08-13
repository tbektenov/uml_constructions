package tbektenov.com.sau.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Hospital;

import java.util.List;

/**
 * Repository interface for {@link Hospital} entities.
 * Provides CRUD operations for managing hospital entities in the database.
 *
 * <p>This interface extends {@link JpaRepository}, which includes methods for
 * basic CRUD operations, pagination, and sorting.</p>
 *
 * @see JpaRepository
 * @see Hospital
 */
public interface HospitalRepo
        extends JpaRepository<Hospital, Long> {

    @EntityGraph(value = "Hospital.withPharmaciesAndDoctors", type = EntityGraph.EntityGraphType.FETCH)
    Page<Hospital> findAll(Pageable pageable);
}
