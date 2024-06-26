package tbektenov.com.sau.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tbektenov.com.sau.models.user.Sex;
import tbektenov.com.sau.models.user.userRoles.BloodGroup;
import tbektenov.com.sau.models.user.userRoles.RhFactor;
import tbektenov.com.sau.models.user.userRoles.Specialization;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for user registration.
 *
 * This DTO contains the necessary details for a new user registration,
 * including personal information, account credentials, and contact details.
 */
@Data
public class RegisterDTO {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthdate;
    private String pesel;
    private Sex sex;
    private String ssn;
    private RhFactor rhFactor;
    private BloodGroup bloodGroup;
    private Specialization specialization;
    private Long hospitalId;
    private Boolean isNurse;
}
