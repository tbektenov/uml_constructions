package tbektenov.com.sau.controllers.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbektenov.com.sau.dtos.user.LoginDTO;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.user.UserRole;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.repositories.PatientRepo;
import tbektenov.com.sau.repositories.UserRepo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private UserRepo userRepo;
    private PatientRepo patientRepo;
    private HospitalRepo hospitalRepo;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepo userRepo,
                          PatientRepo patientRepo,
                          HospitalRepo hospitalRepo,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.patientRepo = patientRepo;
        this.hospitalRepo = hospitalRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user based on their username and password.
     *
     * @param loginDTO the DTO containing the user's login credentials
     * @return a response message indicating success or failure of the login attempt
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        Optional<UserEntity> userOptional = userRepo.findByUsername(loginDTO.getUsername());

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("Username does not exist", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userOptional.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User signed in.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Registers a new user and assigns them the patient role.
     *
     * @param registerDTO the DTO containing the user's registration details
     * @return a response message indicating the success or failure of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        UserEntity user = new UserEntity();
        Set<UserRole> userRoles = new HashSet<>();

        user.setName(registerDTO.getName());
        user.setSurname(registerDTO.getSurname());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setBirthdate(registerDTO.getBirthdate());
        user.setPesel(registerDTO.getPesel());
        user.setSex(registerDTO.getSex());

        if (registerDTO.getSsn() != null) {
            Patient patient = new Patient();

            if (registerDTO.getBloodGroup() != null) patient.setBloodGroup(registerDTO.getBloodGroup());
            if (registerDTO.getRhFactor() != null) patient.setRhFactor(registerDTO.getRhFactor());

            userRoles.add(UserRole.PATIENT);

            patient.setUser(user);
            user.setPatient(patient);
        }

        if (registerDTO.getSpecialization() != null && registerDTO.getHospitalId() != null) {
            Hospital hospital = hospitalRepo.findById(registerDTO.getHospitalId()).orElseThrow(
                    () -> new ObjectNotFoundException("No such hospital.")
            );

            Doctor doctor = new Doctor();
            doctor.setSpecialization(registerDTO.getSpecialization());
            doctor.setHospital(hospital);

            userRoles.add(UserRole.DOCTOR);

            doctor.setUser(user);
            user.setDoctor(doctor);
        }

        if (registerDTO.getIsNurse() != null && registerDTO.getIsNurse()) {
            Nurse nurse = new Nurse();

            userRoles.add(UserRole.NURSE);

            nurse.setUser(user);
            user.setNurse(nurse);
        }

        user.setRoles(userRoles);

        userRepo.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
