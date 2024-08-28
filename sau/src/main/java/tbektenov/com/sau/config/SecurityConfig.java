package tbektenov.com.sau.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security, enabling web security and configuring security-related beans.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Constructs a new {@code SecurityConfig} with the provided {@link CustomUserDetailsService}.
     *
     * @param userDetailsService the custom user details service for loading user-specific data during authentication.
     */
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Creates and configures the {@link AuthenticationManager} bean.
     *
     * @param authenticationConfiguration the authentication configuration provided by Spring Security.
     * @return the configured {@link AuthenticationManager}.
     * @throws Exception if an error occurs while configuring the {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures and returns a {@link PasswordEncoder} bean using the BCrypt hashing algorithm.
     *
     * @return a {@link PasswordEncoder} bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain, including HTTP security, form login, logout, and authorization rules.
     *
     * @param http the {@link HttpSecurity} to modify.
     * @return the configured {@link SecurityFilterChain}.
     * @throws Exception if an error occurs while configuring the security filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/login?error=true",
                                "/login?logout",
                                "/h2-console/**"
                        ).permitAll()
                        .requestMatchers("/appointments/new").hasAuthority("PATIENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe.disable())
                .headers(header -> header
                        .frameOptions(frameOptionsConfig -> frameOptionsConfig.disable())
                );

        return http.build();
    }
}
