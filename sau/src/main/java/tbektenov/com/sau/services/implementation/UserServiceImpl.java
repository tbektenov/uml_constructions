package tbektenov.com.sau.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.repositories.UserRepo;
import tbektenov.com.sau.services.IUserService;

@Service
public class UserServiceImpl
    implements IUserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String deleteUserById(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("No such user.")
        );

        userRepo.delete(user);
        return String.format("User with id: %d was deleted", id);
    }
}
