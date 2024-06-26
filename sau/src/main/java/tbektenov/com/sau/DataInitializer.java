package tbektenov.com.sau;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tbektenov.com.sau.repositories.*;

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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {
        LOG.info("Starting data initialization");


    }
}