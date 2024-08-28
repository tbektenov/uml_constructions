package tbektenov.com.sau.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.UserRole;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.repositories.HospitalRepo;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IUserService;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the IUserService interface for managing user-related operations.
 * Handles user registration, deletion, and role assignment.
 */
@Service
public class UserServiceImpl implements IUserService {

    private UserRepo userRepo;
    private HospitalRepo hospitalRepo;
    private PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserServiceImpl with the specified repositories and services.
     *
     * @param userRepo             The repository for user entities.
     * @param hospitalRepo         The repository for hospital entities.
     * @param passwordEncoder      The password encoder for encoding user passwords.
     */
    @Autowired
    public UserServiceImpl(UserRepo userRepo,
                           HospitalRepo hospitalRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.hospitalRepo = hospitalRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user based on the provided RegisterDTO.
     * This method creates a UserEntity and associates it with roles such as Patient, Doctor, or Nurse
     * based on the information in the RegisterDTO.
     *
     * @param registerDTO The DTO containing registration details for the user.
     */
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
            nurse.setHospitalizations(new HashSet<>());
            userRoles.add(UserRole.NURSE);

            user.setNurse(nurse);
        }

        user.setRoles(userRoles);

        userRepo.save(user);
    }

    /**
     * Creates a UserEntity from the provided RegisterDTO.
     *
     * @param registerDTO The DTO containing user registration details.
     * @return The created UserEntity.
     */
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

    /**
     * Creates a Patient entity from the provided RegisterDTO and associates it with the given UserEntity.
     *
     * @param registerDTO The DTO containing patient details.
     * @param user        The UserEntity to associate with the Patient.
     * @return The created Patient entity.
     */
    private Patient createPatientFromDTO(RegisterDTO registerDTO, UserEntity user) {
        Patient patient = new Patient();
        patient.setBloodGroup(registerDTO.getBloodGroup());
        patient.setRhFactor(registerDTO.getRhFactor());
        patient.setSsn(registerDTO.getSsn());
        patient.setUser(user);
        return patient;
    }

    /**
     * Creates a Doctor entity from the provided RegisterDTO and associates it with the given UserEntity.
     *
     * @param registerDTO The DTO containing doctor details.
     * @param user        The UserEntity to associate with the Doctor.
     * @return The created Doctor entity.
     * @throws ObjectNotFoundException if no hospital with the specified ID is found.
     */
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
