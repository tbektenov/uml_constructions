package tbektenov.com.sau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 *
 * <p>The {@code SauApplication} class is annotated with {@link SpringBootApplication}, which is a
 * convenience annotation that adds the following:
 * <ul>
 *   <li>{@code @Configuration}: Tags the class as a source of bean definitions for the application context.</li>
 *   <li>{@code @EnableAutoConfiguration}: Tells Spring Boot to start adding beans based on classpath settings,
 *       other beans, and various property settings.</li>
 *   <li>{@code @ComponentScan}: Tells Spring to look for other components, configurations, and services in the
 *       {@code tbektenov.com.sau} package, allowing it to find the controllers.</li>
 * </ul>
 *
 * <p>The {@code main} method uses {@link SpringApplication#run} to launch the application. This method
 * initializes the Spring application context, starts the embedded server (if applicable), and deploys the application.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     public static void main(String[] args) {
 *         SpringApplication.run(SauApplication.class, args);
 *     }
 * </pre>
 *
 * @see SpringApplication
 * @see SpringBootApplication
 */
@SpringBootApplication
public class SauApplication {

	/**
	 * Main method that serves as the entry point of the Spring Boot application.
	 *
	 * @param args Command line arguments passed to the application (typically not used).
	 */
	public static void main(String[] args) {
		SpringApplication.run(SauApplication.class, args);
	}

}
