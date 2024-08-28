package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.doctor.DoctorDTO;

import java.util.List;

/**
 * Service interface for handling doctor-related operations.
 *
 * This interface defines methods for managing doctors, including retrieving, creating, updating,
 * deleting, and managing their association with hospitals.
 */
public interface IDoctorService {
    List<DoctorDTO> getAllDoctors();
}
