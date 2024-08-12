package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;

public interface LeftPatientRepo
    extends JpaRepository<LeftPatient, Long> {
}
