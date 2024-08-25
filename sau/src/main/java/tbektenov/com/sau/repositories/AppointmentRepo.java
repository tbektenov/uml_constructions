package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.AppointmentStatus;

import java.util.List;
import java.util.Optional;

/**
 * IAppointmentRepo is a repository interface for managing {@link Appointment} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD (Create, Read, Update, Delete) operations
 * and additional methods for querying {@link Appointment} data from the database.
 * <p>
 * By extending JpaRepository, IAppointmentRepo inherits several methods for working with {@link Appointment} persistence,
 * including methods for saving, deleting, and finding {@link Appointment} entities.
 * Spring Data JPA will automatically implement this repository interface in a bean that has the same name (with a capital first letter),
 * typically something like {@code appointmentRepo}.
 * </p>
 * <p>
 * The Integer type parameter in {@link JpaRepository<Appointment, Integer>} specifies the type of the primary key of the {@link Appointment} entity.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * @Autowired
 * private IAppointmentRepo appointmentRepo;
 *
 * public void someMethod() {
 *     // Saving an appointment
 *     Appointment appointment = new Appointment();
 *     appointmentRepo.save(appointment);
 *
 *     // Finding an appointment by ID
 *     Appointment foundAppointment = appointmentRepo.findById(1).orElse(null);
 *
 *     // Deleting an appointment
 *     appointmentRepo.delete(foundAppointment);
 * }
 * }</pre>
 * </p>
 * <p>
 * Note: This repository is part of the Spring Data JPA infrastructure and provides additional
 * functionalities like pagination and custom query support out of the box.
 * </p>
 *
 * @see JpaRepository
 * @see Appointment
 */
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);
    @EntityGraph(value = "Appointment.details", type = EntityGraph.EntityGraphType.FETCH)
    List<Appointment> findByPatientIdAndAppointmentStatus(Long patientId, AppointmentStatus status);

    Boolean existsByPatientIdAndDoctorId(Long patientId, Long doctorId);
}
