package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;

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
    Boolean existsByAddressAndPharmaCompany(String address, String pharmaCompany);
}

