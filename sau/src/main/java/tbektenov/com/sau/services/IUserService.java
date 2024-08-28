package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.user.RegisterDTO;

public interface IUserService {
    void registerUser(RegisterDTO registerDTO);
}
