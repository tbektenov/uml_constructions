package tbektenov.com.sau.controllers.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tbektenov.com.sau.config.CustomUserDetailsService;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IUserService;
import tbektenov.com.sau.services.implementation.AppointmentServiceImpl;

import java.util.List;

/**
 * Controller class for managing user authentication and registration.
 */
@Controller
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    private final AppointmentServiceImpl appointmentService;

    /**
     * Constructs an {@code AuthController} with the specified dependencies.
     *
     * @param userRepo the repository for accessing user data
     * @param authenticationManager the manager for handling authentication processes
     * @param passwordEncoder the encoder for handling password encryption
     * @param userService the service for managing user-related operations
     * @param appointmentService the service for managing appointment-related operations
     * @param customUserDetailsService the service for loading user-specific details
     */
    @Autowired
    public AuthController(UserRepo userRepo,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          IUserService userService,
                          AppointmentServiceImpl appointmentService,
                          CustomUserDetailsService customUserDetailsService) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Displays the welcome page.
     *
     * @return the name of the welcome page view
     */
    @GetMapping("/")
    public String showWelcomePage() {
        return "welcomePage";
    }

    /**
     * Displays the login page.
     *
     * @param model the model to carry data to the view
     * @param error an optional parameter indicating if there was a login error
     * @return the name of the login page view
     */
    @GetMapping("/login")
    public String showLogin(
            Model model,
            @RequestParam(name = "error", required = false) Boolean error
    ) {
        if (error != null && error) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }

    /**
     * Displays the home page with the user's upcoming appointments.
     *
     * @param model the model to carry data to the view
     * @param session the HTTP session to store and retrieve user data
     * @return the name of the home page view
     */
    @GetMapping("/home")
    public String showHomePage(
            Model model,
            HttpSession session
    ) {
        UserEntity user;
        if (session.getAttribute("user") == null) {
            user = customUserDetailsService.getLoggedUser();
            session.setAttribute("user", user);
        } else {
            user = (UserEntity) session.getAttribute("user");
        }

        List<AppointmentDTO> appointments = appointmentService.getUpcomingAppointmentsByPatientId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("appointments", appointments);
        return "home";
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
