package tbektenov.com.sau.models.user.userRoles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.user.UserEntity;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Doctor.hospitalAndLaboratory",
                attributeNodes = {
                        @NamedAttributeNode("hospital"),
                        @NamedAttributeNode("laboratory"),
                        @NamedAttributeNode("user")
                }
        )
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor{

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, updatable = false)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @NotNull(message = "Doctor must have specialization.")
    @Column(name = "specialization")
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratory_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Laboratory laboratory;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderEntity> orders = new HashSet<>();
}
