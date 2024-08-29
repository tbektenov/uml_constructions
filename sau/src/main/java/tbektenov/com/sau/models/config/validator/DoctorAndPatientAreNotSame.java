package tbektenov.com.sau.models.config.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DoctorAndPatientAreNotSameValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DoctorAndPatientAreNotSame {
    String message() default "Doctor and Patient are the same person.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
