package tbektenov.com.sau.controllers.user;

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
import tbektenov.com.sau.config.LoggedUserHolder;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IUserService;

import java.util.Optional;

@Controller
public class AuthController {
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IUserService userService;
    private LoggedUserHolder loggedUserHolder;

    @Autowired
    public AuthController(UserRepo userRepo,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          IUserService userService,
                          LoggedUserHolder loggedUserHolder) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.loggedUserHolder = loggedUserHolder;
    }

    @GetMapping("/")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String showHome() {
        UserEntity user = loggedUserHolder.getLoggedUser();
        System.out.println(user);
        return "home";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Optional<UserEntity> userOptional = userRepo.findByUsername(username);

        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Username does not exist");
            return "login";
        }

        UserEntity user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Invalid password");
            return "login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "Authentication failed");
            return "login";
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
        userService.registerUser(registerDTO);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
