package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.UserEntity;

import java.util.Optional;

/**
 * Repository interface for UserEntity.
 *
 * This interface extends JpaRepository to provide CRUD operations for UserEntity entities,
 * and includes custom methods for finding a user by their username and checking if a username exists.
 */
public interface UserRepo
    extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a UserEntity by its username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the found UserEntity, or empty if no user is found.
     */
    @EntityGraph(value = "User.details", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserEntity> findByUsername(String username);

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username The username to check for existence.
     * @return True if a user with the given username exists, otherwise false.
     */
    Boolean existsByUsername(String username);
    Boolean existsByPesel(String pesel);

}
