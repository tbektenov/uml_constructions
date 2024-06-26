package tbektenov.com.sau.models.hospital;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "HOSPITAL_WARD")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalWard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_ward_id", nullable = false)
    private Long id;

    @NotBlank(message = "Ward number cannot be null or empty")
    @Column(name = "wardNum", nullable = false)
    private String wardNum;

    @NotNull
    @Min(value = 1, message = "Capacity of ward should be at least 1.")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hospital hospital;
}
