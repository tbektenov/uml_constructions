package tbektenov.com.sau.models.user.userRoles;

import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;

public interface IDoctor {

    /**
     * Assigns a nurse to a specific hospitalization.
     *
     * @param nurse the nurse to be assigned
     * @param hospitalization the hospitalization to which the nurse is assigned
     */
    void assignNurseToHospitalization(Nurse nurse, Hospitalization hospitalization);

    /**
     * Adds an appointment to the doctor's list of appointments.
     *
     * @param appointment the appointment to add
     */
    void addAppointmentToDoctor(Appointment appointment);

    /**
     * Sets the doctor's laboratory. Removes the doctor from the current lab if different.
     * Throws an exception if the lab is from a different hospital.
     *
     * @param laboratory The new lab to associate with. If null, clears the current lab.
     * @throws InvalidArgumentsException if the lab is from a different hospital.
     */
    void setLaboratory(Laboratory laboratory);

    /**
     * Sends an order from this doctor to the specified hospital pharmacy.
     *
     * @param pharmacy the hospital pharmacy to which the order is sent
     * @param order    the content of the order
     * @return the created order entity
     * @throws InvalidArgumentsException if the pharmacy is null, the order content is null or empty,
     *                                   or the doctor is from a different hospital
     */
    OrderEntity sendOrderToHospPharmacy(HospitalPharmacy pharmacy, String order);
}
