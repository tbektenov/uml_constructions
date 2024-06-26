package tbektenov.com.sau.models.user.patientRoles.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tbektenov.com.sau.models.user.userRoles.Patient;

public class OnePatientValidator implements ConstraintValidator<OnePatientCheck, Patient> {

    @Override
    public void initialize(OnePatientCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(Patient patient, ConstraintValidatorContext context) {
        if (patient == null) {
            return true;
        }
        return patient.getStayingPatient() == null || patient.getLeftPatient() == null;
    }
}
