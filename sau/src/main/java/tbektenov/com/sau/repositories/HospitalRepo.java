package tbektenov.com.sau.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.user.userRoles.Doctor;

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT h FROM Hospital h LEFT JOIN FETCH h.partnerPharmacies WHERE h.name = :name")
    Optional<Hospital> findByNameWithPharmacies(@Param("name") String name);

    Boolean existsByName(String name);
    Optional<Hospital> findByName(String name);
}
