package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.user.UserEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nurse{
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false, updatable = false)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Column(name = "surname", nullable = false, updatable = false)
    private String surname;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "nurse_hospitalization",
            joinColumns = @JoinColumn(name = "nurse_id"),
            inverseJoinColumns = @JoinColumn(name = "hospitalization_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Hospitalization> hospitalizations = new HashSet<>();
}
