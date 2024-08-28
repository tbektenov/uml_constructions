package tbektenov.com.sau.services;

import tbektenov.com.sau.dtos.hospital.*;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyDTO;

import javax.print.Doc;
import java.util.List;

/**
 * Service interface for managing hospitals.
 * Provides methods for creating, retrieving, updating, and deleting hospitals.
 */
public interface IHospitalService {
    /**
     * Retrieves a paginated list of all hospitals.
     *
     * @param pageNo   the page number to retrieve
     * @param pageSize the number of records per page
     * @return a {@link HospitalResponse} containing the list of hospitals and pagination details
     */
    HospitalResponse getAllHospitals(int pageNo, int pageSize);
}
