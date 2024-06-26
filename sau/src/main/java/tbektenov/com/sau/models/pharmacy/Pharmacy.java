package tbektenov.com.sau.models.pharmacy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Abstract base class representing a Pharmacy.
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pharmacy_id", nullable = false)
    protected Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "isCompoundPharmacy", nullable = false)
    protected boolean isCompoundPharmacy = false;
}
