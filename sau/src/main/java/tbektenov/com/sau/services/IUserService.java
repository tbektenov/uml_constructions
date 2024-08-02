package tbektenov.com.sau.services;

import org.springframework.http.ResponseEntity;
import tbektenov.com.sau.dtos.user.LoginDTO;
import tbektenov.com.sau.dtos.user.RegisterDTO;

public interface IUserService {
    String deleteUserById(Long id);

    void registerUser(RegisterDTO registerDTO);

    ResponseEntity<String> login(LoginDTO loginDTO);
}
