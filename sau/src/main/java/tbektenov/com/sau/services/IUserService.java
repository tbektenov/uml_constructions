package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.user.RegisterDTO;

public interface IUserService {
    String deleteUserById(Long id);

    void registerUser(RegisterDTO registerDTO);
}
