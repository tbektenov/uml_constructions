package tbektenov.com.sau;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.user.Sex;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.userRoles.*;
import tbektenov.com.sau.repositories.*;

import javax.print.Doc;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private AppointmentRepo appointmentRepo;
    private HospitalizationRepo hospitalizationRepo;
    private HospitalPharmacyRepo hospitalPharmacyRepo;
    private HospitalRepo hospitalRepo;
    private HospitalWardRepo hospitalWardRepo;
    private LaboratoryRepo laboratoryRepo;
    private OrderRepo orderRepo;
    private PrivatePharmacyRepo privatePharmacyRepo;
    private UserRepo userRepo;
    private DoctorRepo doctorRepo;
    private PatientRepo patientRepo;
    private NurseRepo nurseRepo;

    @Autowired
    public DataInitializer(AppointmentRepo appointmentRepo,
                           HospitalizationRepo hospitalizationRepo,
                           HospitalPharmacyRepo hospitalPharmacyRepo,
                           HospitalRepo hospitalRepo,
                           HospitalWardRepo hospitalWardRepo,
                           LaboratoryRepo laboratoryRepo,
                           OrderRepo orderRepo,
                           PrivatePharmacyRepo privatePharmacyRepo,
                           UserRepo userRepo,
                           DoctorRepo doctorRepo,
                           PatientRepo patientRepo,
                           NurseRepo nurseRepo) {
        this.appointmentRepo = appointmentRepo;
        this.hospitalizationRepo = hospitalizationRepo;
        this.hospitalPharmacyRepo = hospitalPharmacyRepo;
        this.hospitalRepo = hospitalRepo;
        this.hospitalWardRepo = hospitalWardRepo;
        this.laboratoryRepo = laboratoryRepo;
        this.orderRepo = orderRepo;
        this.privatePharmacyRepo = privatePharmacyRepo;
        this.userRepo = userRepo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.nurseRepo = nurseRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {
        LOG.info("Starting data initialization");

        Hospital hospital = Hospital.builder()
                .name("SAU")
                .address("Chal 3a")
                .build();

        hospitalRepo.save(hospital);

        String username = "s26218";
        if (!userRepo.existsByUsername(username)) {
            UserEntity user = UserEntity.builder()
                    .name("Tagir")
                    .surname("Bektenov")
                    .password("123456")
                    .username(username)
                    .email("example@example.com")
                    .phoneNumber("660-123-1732")
                    .birthdate(LocalDate.of(2004,4,11))
                    .pesel("94111315115")
                    .sex(Sex.MALE)
                    .build();

            userRepo.save(user);
        }

    }
}