package tbektenov.com.sau.models.config.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tbektenov.com.sau.models.Hospitalization;

public class AtLeastOneNurseValidator implements ConstraintValidator<AtLeastOneNurse, Hospitalization> {
    @Override
    public void initialize(AtLeastOneNurse constraintAnnotation) {
    }

    @Override
    public boolean isValid(Hospitalization hospitalization, ConstraintValidatorContext context) {
        return hospitalization.getNurses() != null && !hospitalization.getNurses().isEmpty();
    }
}
