package tbektenov.com.sau.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tbektenov.com.sau.services.IUserService;

/**
 * Controller class for managing user-related operations.
 */
@Controller
@RequestMapping("/api/user/")
public class UserController {

    private final IUserService userService;

    /**
     * Constructs a {@code UserController} with the specified user service.
     *
     * @param userService the service for managing user-related operations
     */
    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a response message indicating the success of the deletion
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}
