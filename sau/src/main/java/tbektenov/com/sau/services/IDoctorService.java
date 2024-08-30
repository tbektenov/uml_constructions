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

    /**
     * Retrieves all doctors from the repository and maps them to DoctorDTO objects.
     *
     * @return A list of DoctorDTO representing all doctors.
     */
    List<DoctorDTO> getAllDoctors();
}
