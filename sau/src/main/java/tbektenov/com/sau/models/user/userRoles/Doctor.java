package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.*;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.user.UserEntity;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a doctor entity associated with a user, hospital, and optionally a laboratory.
 *
 * <p>This class handles the relationship of a doctor with appointments, orders, and hospitalizations.
 * It is mapped to the database using JPA annotations.</p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Doctor.detailsHospitalAndLaboratory",
                attributeNodes = {
                        @NamedAttributeNode("appointments"),
                        @NamedAttributeNode("hospital"),
                        @NamedAttributeNode("laboratory"),
                        @NamedAttributeNode(value = "user", subgraph = "user.subgraph")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "user.subgraph",
                                attributeNodes = {
                                        @NamedAttributeNode("doctor"),
                                        @NamedAttributeNode(value = "patient", subgraph = "patient.subgraph"),
                                        @NamedAttributeNode("nurse")
                                }
                        ),
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
                }
        )
})
public class Doctor
    implements IDoctor{

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @NotNull(message = "Doctor must have specialization.")
    @Column(name = "specialization")
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratory_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Laboratory laboratory;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderEntity> orders = new HashSet<>();

    /**
     * Marks the given appointment as finished by setting its status to `ARCHIVED`.
     *
     * @param appointment The appointment to mark as finished.
     * @return The updated appointment.
     * @throws InvalidArgumentsException If the appointment is not assigned to this doctor.
     */
    @Override
    public Appointment finishAppointment(Appointment appointment) {
        if (appointment != null && this.appointments.contains(appointment)) {
            appointment.setAppointmentStatus(AppointmentStatus.ARCHIVED);
            return appointment;
        } else {
            throw new InvalidArgumentsException("appointment is not assigned to this doctor.");
        }
    }

    /**
     * Cancels the given appointment by removing it from the doctor's list.
     *
     * @param appointment The appointment to cancel.
     * @throws InvalidArgumentsException If the appointment is not assigned to this doctor.
     */
    @Override
    public void cancelAppointmentForDoctor(Appointment appointment) {
        if (appointment != null && this.appointments.contains(appointment)) {
            appointments.remove(appointment);
        } else {
            throw new InvalidArgumentsException("appointment is not assigned to this doctor.");
        }
    }

    /**
     * Assigns a nurse to a specific hospitalization.
     *
     * @param nurse the nurse to be assigned
     * @param hospitalization the hospitalization to which the nurse is assigned
     */
    @Override
    public void assignNurseToHospitalization(Nurse nurse,
                                             Hospitalization hospitalization) {
        if (nurse != null && hospitalization != null) {

            if (Objects.equals(nurse.getId(), hospitalization.getPatient().getId())) {
                throw new InvalidArgumentsException("Nurse and patient are the same person.");
            }

            if (nurse.getHospitalizations().contains(hospitalization)) {
                throw new InvalidArgumentsException("Nurse is already assigned to this hospitalization.");
            }

            nurse.addHospitalization(hospitalization);
            if (!nurse.getHospitalizations().contains(hospitalization)) {
                hospitalization.addNurse(nurse);
            }
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
        if (pharmacy == null) {
            throw new InvalidArgumentsException("pharmacy is null.");
        }

        if (order == null || order.isEmpty()) {
            throw new InvalidArgumentsException("order is null or empty.");
        }

        if (!pharmacy.getHospital().getId().equals(this.hospital.getId())) {
            throw new InvalidArgumentsException("doctor is from another hospital.");
        }

        OrderEntity newOrder = OrderEntity.builder()
                .doctor(this)
                .orderBody(order)
                .hospitalPharmacy(pharmacy)
                .orderStatus(OrderStatus.ONGOING)
                .dateTimeOfIssue(LocalDateTime.now())
                .build();

        if (!pharmacy.getOrders().contains(newOrder)) {
            pharmacy.addOrder(newOrder);
        }

        return newOrder;
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
        if (this.laboratory != null && !this.laboratory.equals(laboratory)) {
            if (this.laboratory.getDoctors().contains(this)) {
                this.laboratory.removeDoctor(this);
            }
        }

        if (laboratory == null) {
            this.laboratory = null;
            return;
        }

        if (!Objects.equals(this.hospital.getId(), laboratory.getHospital().getId())) {
            throw new InvalidArgumentsException("Doctor and lab are from different hospitals");
        }

        this.laboratory = laboratory;
        if (!laboratory.getDoctors().contains(this)) {
            laboratory.addDoctor(this);
        }
    }

    /**
     * Sets the hospital for the current doctor and ensures the doctor is added to the hospital's list of doctors.
     *
     * @param hospital the hospital to be assigned to the doctor
     */
    public void setHospital(Hospital hospital) {
        if (hospital != null) {
            this.hospital = hospital;
            if (!hospital.getDoctors().contains(this)) {
                hospital.addDoctor(this);
            }
        }
    }
}
