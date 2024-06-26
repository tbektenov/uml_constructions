package tbektenov.com.sau.models.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.format.annotation.DateTimeFormat;
import tbektenov.com.sau.models.config.converter.UserRoleSetConverter;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name", nullable = false)
    protected String name;

    @NotBlank(message = "Surname cannot be blank.")
    @Column(name = "surname", nullable = false)
    protected String surname;

    @NotBlank(message = "Username cannot be blank.")
    @Column(name = "username", nullable = false, unique = true)
    protected String username;

    @NotBlank(message = "Password cannot be blank.")
    @Column(name = "password", nullable = false)
    protected String password;

    @Email(message = "Email is not valid.")
    @NotBlank(message = "Email cannot be blank.")
    @Column(name = "email", nullable = false)
    protected String email;

    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", message = "Phone number is not valid")
    @NotBlank(message = "Phone number cannot be null.")
    @Column(name = "phone_number", nullable = false)
    protected String phoneNumber;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "birthdate", nullable = false)
    protected LocalDate birthdate;

    @Pattern(regexp = "^[0-9]{2}([02468][1]|[13579][012])(0[1-9]|1[0-9]|2[0-9]|3[01])[0-9]{5}$", message = "PESEL is not valid.")
    @NotBlank(message = "PESEL cannot be blank.")
    @Column(name = "pesel", nullable = false, unique = true)
    protected String pesel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "roles", updatable = false)
    @Convert(converter = UserRoleSetConverter.class)
    private Set<UserRole> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Nurse nurse;

    public UserEntity(String name, String surname, String username, String password, String email, String phoneNumber, LocalDate birthdate, String pesel) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.pesel = pesel;
    }
}

