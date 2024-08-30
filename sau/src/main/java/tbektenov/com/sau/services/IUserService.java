package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.user.RegisterDTO;

public interface IUserService {

    /**
     * Registers a new user based on the provided RegisterDTO.
     * This method creates a UserEntity and associates it with roles such as Patient, Doctor, or Nurse
     * based on the information in the RegisterDTO.
     *
     * @param registerDTO The DTO containing registration details for the user.
     */
    void registerUser(RegisterDTO registerDTO);
}
