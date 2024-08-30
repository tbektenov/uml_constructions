package tbektenov.com.sau.models.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.config.converter.UserRoleSetConverter;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.user.userRoles.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the system.
 *
 * <p>Includes personal details, credentials, and associated roles such as Doctor, Patient, or Nurse.</p>
 *
 * <p>Uses JPA annotations for ORM mapping and Lombok for reducing boilerplate code.</p>
 */
@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "User.details",
                attributeNodes = {
                        @NamedAttributeNode("doctor"),
                        @NamedAttributeNode(value = "patient", subgraph = "patient.subgraph"),
                        @NamedAttributeNode("nurse")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "patient.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "stayingPatient", subgraph = "stayingPatient.subgraph"),
                                        @NamedAttributeNode("leftPatient")
                                }
                        ),
                        @NamedSubgraph(
                                name = "stayingPatient.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode("hospitalization")
                                }
                        )
                })
)
public class UserEntity
    implements IDoctor, IPatient, INurse{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name", nullable = false)
    protected String name;

    @NotBlank(message = "Surname cannot be blank.")
    @Column(name = "surname", nullable = false)
    protected String surname;

    @NotBlank(message = "Username cannot be blank.")
    @Column(name = "username", nullable = false, unique = true)
    protected String username;

    @NotBlank(message = "Password cannot be blank.")
    @Column(name = "password", nullable = false)
    protected String password;

    @Email(message = "Email is not valid.")
    @NotBlank(message = "Email cannot be blank.")
    @Column(name = "email", nullable = false)
    protected String email;

    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", message = "Phone number is not valid")
    @NotBlank(message = "Phone number cannot be null.")
    @Column(name = "phone_number", nullable = false)
    protected String phoneNumber;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "birthdate", nullable = false)
    protected LocalDate birthdate;

    @Pattern(regexp = "^[0-9]{2}([02468][1]|[13579][012])(0[1-9]|1[0-9]|2[0-9]|3[01])[0-9]{5}$", message = "PESEL is not valid.")
    @NotBlank(message = "PESEL cannot be blank.")
    @Column(name = "pesel", nullable = false, unique = true)
    protected String pesel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "roles", updatable = false)
    @Convert(converter = UserRoleSetConverter.class)
    private Set<UserRole> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Nurse nurse;

    public UserEntity(String name, String surname, String username, String password, String email, String phoneNumber, LocalDate birthdate, String pesel) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.pesel = pesel;
    }

    /**
     * Marks the given appointment as finished by setting its status to `ARCHIVED`.
     *
     * @param appointment The appointment to mark as finished.
     * @return The updated appointment.
     * @throws InvalidArgumentsException If the appointment is not assigned to this doctor.
     */
    @Override
    public Appointment finishAppointment(Appointment appointment) {
        try {
            return this.doctor.finishAppointment(appointment);
        } catch (NullPointerException e) {
            throw new NullPointerException("doctor is null");
        }
    }

    /**
     * Cancels the given appointment by removing it from the doctor's list.
     *
     * @param appointment The appointment to cancel.
     */
    @Override
    public void cancelAppointmentForDoctor(Appointment appointment) {
        try {
            this.doctor.cancelAppointmentForDoctor(appointment);
        } catch (NullPointerException e) {
            throw new NullPointerException("doctor is null");
        }
    }

    /**
     * Cancels the given appointment for the patient by removing it from the list.
     *
     * @param appointment The appointment to cancel.
     */
    @Override
    public void cancelAppointmentForPatient(Appointment appointment) {
        try {
            this.patient.cancelAppointmentForPatient(appointment);
        } catch (NullPointerException e) {
            throw new NullPointerException("doctor is null");
        }
    }

    /**
     * Assigns a nurse to a specific hospitalization.
     *
     * @param nurse the nurse to be assigned
     * @param hospitalization the hospitalization to which the nurse is assigned
     */
    @Override
    public void assignNurseToHospitalization(Nurse nurse, Hospitalization hospitalization) {
        if (this.doctor != null) {
            this.doctor.assignNurseToHospitalization(nurse, hospitalization);
        }
    }
    /**
     * Sets the doctor's laboratory. Removes the doctor from the current lab if different.
     * Throws an exception if the lab is from a different hospital.
     *
     * @param laboratory The new lab to associate with. If null, clears the current lab.
     * @throws InvalidArgumentsException if the lab is from a different hospital.
     */
    @Override
    public void setLaboratory(Laboratory laboratory) {
        if (this.doctor != null) {
            this.doctor.setLaboratory(laboratory);
        }
    }

    /**
     * Adds a hospitalization to the nurse's list of hospitalizations.
     *
     * @param hospitalization the hospitalization to add
     */
    @Override
    public void addHospitalization(Hospitalization hospitalization) {
        if (this.nurse != null) {
            this.nurse.addHospitalization(hospitalization);
        }
    }

    /**
     * Removes the given hospitalization from this nurse's list.
     * Also removes the nurse from the hospitalization's list of nurses.
     *
     * @param hospitalization The hospitalization to remove. If null or not found, nothing happens.
     */
    @Override
    public void removeHospitalization(Hospitalization hospitalization) {
        if (this.nurse != null) {
            this.nurse.removeHospitalization(hospitalization);
        }
    }

    /**
     * Returns a set of patients assigned to this nurse through their hospitalizations.
     *
     * @return A set of assigned patients.
     */
    @Override
    public Set<UserEntity> getAssignedPatients() {
        try {
            return this.nurse.getAssignedPatients();
        } catch (NullPointerException e) {
            throw new NullPointerException("User does not have nurse role");
        }
    }

    /**
     * Returns a set of hospital wards assigned to this nurse through their hospitalizations.
     *
     * @return A set of assigned hospital wards.
     */
    @Override
    public Set<HospitalWard> getAssignedWards() {
        try {
            return this.nurse.getAssignedWards();
        } catch (NullPointerException e) {
            throw new NullPointerException("User does not have nurse role");
        }
    }

    /**
     * Sends an order from this doctor to the specified hospital pharmacy.
     *
     * @param pharmacy the hospital pharmacy to which the order is sent
     * @param order    the content of the order
     * @return the created order entity
     * @throws InvalidArgumentsException if the pharmacy is null, the order content is null or empty,
     *                                   or the doctor is from a different hospital
     */
    @Override
    public OrderEntity sendOrderToHospPharmacy(HospitalPharmacy pharmacy, String order) {
        try {
            return this.doctor.sendOrderToHospPharmacy(pharmacy, order);
        } catch (NullPointerException e) {
            throw new NullPointerException("User does not have doctor role");
        }
    }
}

