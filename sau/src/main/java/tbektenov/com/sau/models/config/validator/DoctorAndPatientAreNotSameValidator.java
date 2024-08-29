package tbektenov.com.sau.models.config.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tbektenov.com.sau.models.Appointment;

public class DoctorAndPatientAreNotSameValidator
        implements ConstraintValidator<DoctorAndPatientAreNotSame, Appointment> {

    @Override
    public boolean isValid(Appointment appointment, ConstraintValidatorContext constraintValidatorContext) {
        if (appointment.getPatient() == null || appointment.getDoctor() == null) {
            return true;
        }

        Long patientId = appointment.getPatient().getId();
        Long doctorId = appointment.getDoctor().getId();

        return !patientId.equals(doctorId);
    }

    @Override
    public void initialize(DoctorAndPatientAreNotSame constraintAnnotation) {
    }
}
