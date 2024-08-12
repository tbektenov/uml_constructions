package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;

public interface StayingPatientRepo
    extends JpaRepository<StayingPatient, Long> {
}
