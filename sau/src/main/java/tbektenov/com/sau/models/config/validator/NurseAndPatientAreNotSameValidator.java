package tbektenov.com.sau.models.config.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.user.userRoles.Nurse;

public class NurseAndPatientAreNotSameValidator
        implements ConstraintValidator<NurseAndPatientAreNotSame, Hospitalization> {

    @Override
    public boolean isValid(Hospitalization hospitalization, ConstraintValidatorContext constraintValidatorContext) {
        if (hospitalization == null || hospitalization.getPatient() == null) {
            return true;
        }

        Long patientId = hospitalization.getPatient().getPatient().getUser().getId();

        for (Nurse nurse : hospitalization.getNurses()) {
            if (nurse.getUser().getId().equals(patientId)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void initialize(NurseAndPatientAreNotSame constraintAnnotation) {
    }
}
