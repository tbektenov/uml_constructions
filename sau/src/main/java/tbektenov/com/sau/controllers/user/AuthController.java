package tbektenov.com.sau.controllers.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tbektenov.com.sau.config.CustomUserDetailsService;
import tbektenov.com.sau.config.LoggedUserHolder;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IUserService;
import tbektenov.com.sau.services.implementation.AppointmentServiceImpl;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    private final CustomUserDetailsService customUserDetailsService;
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IUserService userService;
    private LoggedUserHolder loggedUserHolder;
    private AppointmentServiceImpl appointmentService;

    @Autowired
    public AuthController(UserRepo userRepo,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          IUserService userService,
                          LoggedUserHolder loggedUserHolder,
                          AppointmentServiceImpl appointmentService, CustomUserDetailsService customUserDetailsService) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.loggedUserHolder = loggedUserHolder;
        this.appointmentService = appointmentService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/")
    public String showWelcomePage() {
        return "welcomePage";
    }

    @GetMapping("/login")
    public String showLogin(
            Model model,
            @RequestParam(name = "error", required = false) Boolean error
    ) {
        if (error != null && error) {
            model.addAttribute("error", "Invalid username or password.");
        }
        System.out.println("============================");
        return "login";
    }

    @GetMapping("/home")
    public String showHome(
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
        System.out.println("============================");
        List<AppointmentDTO> appointments = appointmentService.getUpcomingAppointmentsByPatientId(
                user.getId()
        );

        model.addAttribute("user", user);
        model.addAttribute("appointments", appointments);
        System.out.println("============================");
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
