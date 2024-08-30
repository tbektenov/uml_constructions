package tbektenov.com.sau.models.user.userRoles;

import tbektenov.com.sau.models.Appointment;

public interface IPatient {

    /**
     * Cancels the given appointment for the patient by removing it from the list.
     */
    void cancelAppointmentForPatient(Appointment appointment);
}
