package tbektenov.com.sau.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.UserRole;
import tbektenov.com.sau.repositories.UserRepo;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom implementation of the {@link UserDetailsService} interface.
 * This service is used to load user-specific data during the authentication process.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    /**
     * Constructs a new {@code CustomUserDetailsService} with the provided {@link UserRepo}.
     *
     * @param userRepo the repository used to access user data from the database.
     */
    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Loads the user by username and returns a {@link UserDetails} object that Spring Security uses for authentication and access control.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated {@link UserDetails} object.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Bad credentials.")
        );

        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Converts a set of {@link UserRole} to a set of {@link GrantedAuthority} objects.
     *
     * @param roles the roles to be converted.
     * @return a set of authorities granted to the user.
     */
    private Set<GrantedAuthority> mapRolesToAuthorities(Set<UserRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }

    /**
     * Retrieves the currently authenticated user from the {@link SecurityContextHolder}.
     *
     * @return the {@link UserEntity} of the currently authenticated user.
     * @throws UsernameNotFoundException if the user could not be found in the database.
     */
    public UserEntity getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
