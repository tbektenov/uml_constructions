package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.AppointmentStatus;

import java.util.List;

/**
 * Repository interface for managing {@link Appointment} entities.
 * <p>
 * Provides basic CRUD operations and custom queries for {@link Appointment} data.
 * </p>
 *
 *  @see JpaRepository
 *  @see Appointment
 */
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    /**
     * Find appointments by patient ID and status, fetching related entities.
     *
     * @param patientId the patient's ID
     * @param status the status of the appointments
     * @return a list of appointments matching the given criteria
     */
    @EntityGraph(value = "Appointment.details", type = EntityGraph.EntityGraphType.FETCH)
    List<Appointment> findByPatientIdAndAppointmentStatus(Long patientId, AppointmentStatus status);

    /**
     * Check if an appointment exists between a specific patient and doctor.
     *
     * @param patientId the patient's ID
     * @param doctorId the doctor's ID
     * @return true if an appointment exists, false otherwise
     */
    Boolean existsByPatientIdAndDoctorId(Long patientId, Long doctorId);
}
