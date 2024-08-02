package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.user.LoginDTO;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.UserRole;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.repositories.DoctorRepo;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IUserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl
    implements IUserService {

    private UserRepo userRepo;
    private HospitalRepo hospitalRepo;
    private DoctorRepo doctorRepo;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo,
                           HospitalRepo hospitalRepo,
                           DoctorRepo doctorRepo,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.hospitalRepo = hospitalRepo;
        this.doctorRepo = doctorRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ResponseEntity<String> login(LoginDTO loginDTO) {
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

    @Override
    @Transactional
    public void registerUser(RegisterDTO registerDTO) {
        UserEntity user = createUserEntityFromDTO(registerDTO);
        Set<UserRole> userRoles = new HashSet<>();

        if (registerDTO.getSsn() != null) {
            Patient patient = createPatientFromDTO(registerDTO, user);

            userRoles.add(UserRole.PATIENT);
            user.setPatient(patient);
        }

        if (registerDTO.getSpecialization() != null && registerDTO.getHospitalId() != null) {
            Doctor doctor = createDoctorFromDTO(registerDTO, user);

            userRoles.add(UserRole.DOCTOR);
            user.setDoctor(doctor);
        }

        if (registerDTO.getIsNurse() != null && registerDTO.getIsNurse()) {
            Nurse nurse = new Nurse();

            nurse.setUser(user);
            userRoles.add(UserRole.NURSE);

            nurse.setUser(user);
            user.setNurse(nurse);
        }

        user.setRoles(userRoles);

        userRepo.save(user);
    }

    @Override
    public String deleteUserById(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("No such user.")
        );

        userRepo.delete(user);
        return String.format("User with id: %d was deleted", id);
    }

    private UserEntity createUserEntityFromDTO(RegisterDTO registerDTO) {
        UserEntity user = new UserEntity();
        user.setName(registerDTO.getName());
        user.setSurname(registerDTO.getSurname());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setBirthdate(registerDTO.getBirthdate());
        user.setPesel(registerDTO.getPesel());
        user.setSex(registerDTO.getSex());
        return user;
    }

    private Patient createPatientFromDTO(RegisterDTO registerDTO, UserEntity user) {
        Patient patient = new Patient();
        patient.setBloodGroup(registerDTO.getBloodGroup());
        patient.setRhFactor(registerDTO.getRhFactor());
        patient.setSsn(registerDTO.getSsn());
        patient.setUser(user);
        return patient;
    }

    private Doctor createDoctorFromDTO(RegisterDTO registerDTO, UserEntity user) {
        Hospital hospital = hospitalRepo.findById(registerDTO.getHospitalId()).orElseThrow(
                () -> new ObjectNotFoundException("No such hospital.")
        );
        Doctor doctor = new Doctor();
        doctor.setSpecialization(registerDTO.getSpecialization());
        doctor.setHospital(hospital);
        doctor.setUser(user);
        return doctor;
    }
}
