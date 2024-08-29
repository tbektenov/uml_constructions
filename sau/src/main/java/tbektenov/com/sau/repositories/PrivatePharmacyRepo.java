package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;

import java.util.Optional;

/**
 * Repository interface for {@link PrivatePharmacy} entities.
 * Provides CRUD operations for managing private pharmacy entities in the database.
 *
 * <p>This interface extends {@link JpaRepository}, which includes methods for
 * basic CRUD operations, pagination, and sorting.</p>
 *
 * @see JpaRepository
 * @see PrivatePharmacy
 */
public interface PrivatePharmacyRepo
        extends JpaRepository<PrivatePharmacy, Long> {

    /**
     * Checks if a {@link PrivatePharmacy} exists with the given address and pharmaceutical company.
     *
     * @param address the address of the pharmacy
     * @param pharmaCompany the name of the pharmaceutical company
     * @return {@code true} if a pharmacy exists with the specified address and company, {@code false} otherwise
     */
    Boolean existsByAddressAndPharmaCompany(String address, String pharmaCompany);

    Optional<PrivatePharmacy> findByAddressAndPharmaCompany(String address, String company);
}

