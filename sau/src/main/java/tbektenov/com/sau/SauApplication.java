package tbektenov.com.sau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 *
 * <p>The {@code main} method starts the application by calling {@link SpringApplication#run}.</p>
 *
 * @see SpringApplication
 * @see SpringBootApplication
 */
@SpringBootApplication
public class SauApplication {

	/**
	 * Main method to start the Spring Boot application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SauApplication.class, args);
	}

}
