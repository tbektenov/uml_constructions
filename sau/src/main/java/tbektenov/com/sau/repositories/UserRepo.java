package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.user.UserEntity;

import java.util.Optional;

/**
 * Repository interface for {@link UserEntity} entities.
 * Provides CRUD operations for managing user entities in the database,
 * including custom methods for finding a user by username and checking username existence.
 */
public interface UserRepo
    extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a {@link UserEntity} by its username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the found UserEntity, or empty if no user is found.
     */
    @EntityGraph(value = "User.details", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserEntity> findByUsername(String username);

    /**
     * Checks if a user with the specified username exists in the database.
     *
     * @param username The username to check.
     * @return True if a user with the given username exists, otherwise false.
     */
    Boolean existsByUsername(String username);

}
