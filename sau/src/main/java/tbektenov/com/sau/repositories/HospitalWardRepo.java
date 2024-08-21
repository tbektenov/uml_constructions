package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.HospitalWard;

import java.util.List;

/**
 * Repository interface for HospitalWard entity.
 *
 * This interface extends JpaRepository to provide CRUD operations for HospitalWard entities
 * and includes a custom method to find hospital wards by the hospital's ID.
 */
public interface HospitalWardRepo
    extends JpaRepository<HospitalWard, Long> {
    List<HospitalWard> findByHospitalId(Long hospitalId);
    Boolean existsByWardNumAndHospital(String wardNum, Hospital hospital);
}
