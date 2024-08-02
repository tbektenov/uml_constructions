package tbektenov.com.sau.controllers.user;

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
import org.springframework.web.bind.annotation.RestController;
import tbektenov.com.sau.dtos.user.LoginDTO;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IUserService;
import tbektenov.com.sau.services.implementation.UserServiceImpl;

import java.util.Optional;

@RestController
public class AuthController {
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IUserService userService;

    @Autowired
    public AuthController(UserRepo userRepo,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          IUserService userService) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    /**
     * Authenticates a user based on their username and password.
     *
     * @param loginDTO the DTO containing the user's login credentials
     * @return a response message indicating success or failure of the login attempt
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    /**
     * Registers a new user and assigns them the patient role.
     *
     * @param registerDTO the DTO containing the user's registration details
     * @return a response message indicating the success or failure of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        userService.registerUser(registerDTO);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
