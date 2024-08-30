package tbektenov.com.sau.models.user.userRoles;

import tbektenov.com.sau.models.Appointment;

public interface IPatient {

    /**
     * Adds an appointment to the patient's list of appointments.
     *
     * @param appointment the appointment to add
     */
    void addAppointmentToPatient(Appointment appointment);
}
