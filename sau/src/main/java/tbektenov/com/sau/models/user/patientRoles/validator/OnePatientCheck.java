package tbektenov.com.sau.models.user.patientRoles.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OnePatientValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface OnePatientCheck {
    String message() default "Only one of stayingPatient or leftPatient can be non-null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

