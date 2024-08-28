package tbektenov.com.sau.controllers.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tbektenov.com.sau.config.CustomUserDetailsService;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.services.IUserService;
import tbektenov.com.sau.services.implementation.AppointmentServiceImpl;

import java.util.List;

/**
 * Controller class for managing user authentication and registration.
 */
@Controller
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;
    private final IUserService userService;
    private final AppointmentServiceImpl appointmentService;

    /**
     * Constructs an {@code AuthController} with the specified dependencies.
     *
     * @param userService the service for managing user-related operations
     * @param appointmentService the service for managing appointment-related operations
     * @param customUserDetailsService the service for loading user-specific details
     */
    @Autowired
    public AuthController(
                          IUserService userService,
                          AppointmentServiceImpl appointmentService,
                          CustomUserDetailsService customUserDetailsService) {
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
}
