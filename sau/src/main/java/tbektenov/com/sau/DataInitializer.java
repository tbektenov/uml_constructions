package tbektenov.com.sau;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.AppointmentStatus;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.TreatmentTracker;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;
import tbektenov.com.sau.models.user.Sex;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.userRoles.Specialization;
import tbektenov.com.sau.repositories.*;
import tbektenov.com.sau.services.implementation.PrivatePharmacyServiceImpl;
import tbektenov.com.sau.services.implementation.UserServiceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final StayingPatientRepo stayingPatientRepo;
    private final NurseRepo nurseRepo;
    private AppointmentRepo appointmentRepo;
    private HospitalizationRepo hospitalizationRepo;
    private HospitalPharmacyRepo hospitalPharmacyRepo;
    private HospitalRepo hospitalRepo;
    private HospitalWardRepo hospitalWardRepo;
    private LaboratoryRepo laboratoryRepo;
    private OrderRepo orderRepo;
    private UserRepo userRepo;
    private UserServiceImpl userService;
    private PrivatePharmacyRepo privatePharmacyRepo;
    private PrivatePharmacyServiceImpl privatePharmacyService;

    @Autowired
    public DataInitializer(AppointmentRepo appointmentRepo,
                           HospitalizationRepo hospitalizationRepo,
                           HospitalPharmacyRepo hospitalPharmacyRepo,
                           HospitalRepo hospitalRepo,
                           HospitalWardRepo hospitalWardRepo,
                           LaboratoryRepo laboratoryRepo,
                           OrderRepo orderRepo,
                           UserRepo userRepo,
                           UserServiceImpl userService,
                           PrivatePharmacyServiceImpl privatePharmacyService,
                           PrivatePharmacyRepo privatePharmacyRepo,
                           PatientRepo patientRepo,
                           DoctorRepo doctorRepo, StayingPatientRepo stayingPatientRepo, NurseRepo nurseRepo) {
        this.appointmentRepo = appointmentRepo;
        this.hospitalizationRepo = hospitalizationRepo;
        this.hospitalPharmacyRepo = hospitalPharmacyRepo;
        this.hospitalRepo = hospitalRepo;
        this.hospitalWardRepo = hospitalWardRepo;
        this.laboratoryRepo = laboratoryRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.privatePharmacyService = privatePharmacyService;
        this.privatePharmacyRepo = privatePharmacyRepo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.stayingPatientRepo = stayingPatientRepo;
        this.nurseRepo = nurseRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    @Transactional
    protected void initData() {
        LOG.info("Starting data initialization");

        String hospName = "SAU";
        Hospital sau;
        if (!hospitalRepo.existsByName(hospName)) {
            Hospital hospital = Hospital.builder()
                    .name(hospName)
                    .address("Chal 3a")
                    .laboratories(new HashSet<>())
                    .hospitalPharmacies(new HashSet<>())
                    .hospitalWards(new HashSet<>())
                    .partnerPharmacies(new HashSet<>())
                    .doctors(new HashSet<>())
                    .build();

            sau = hospitalRepo.save(hospital);
        } else {
            sau = hospitalRepo.findByName(hospName).orElseThrow(() -> new RuntimeException("Hospital not found"));
        }

        String hospName1 = "MAU";
        Hospital mau;
        if (!hospitalRepo.existsByName(hospName1)) {
            Hospital hospital = Hospital.builder()
                    .name(hospName1)
                    .address("Togolok Moldo, 4a")
                    .laboratories(new HashSet<>())
                    .hospitalPharmacies(new HashSet<>())
                    .hospitalWards(new HashSet<>())
                    .partnerPharmacies(new HashSet<>())
                    .doctors(new HashSet<>())
                    .build();

            mau = hospitalRepo.save(hospital);
        } else {
            mau = hospitalRepo.findByName(hospName1).orElseThrow(() -> new RuntimeException("Hospital not found"));
        }

        String hospName2 = "DAU";
        Hospital dau;
        if (!hospitalRepo.existsByName(hospName2)) {
            Hospital hospital = Hospital.builder()
                    .name(hospName2)
                    .address("Chyngyz Aitmatov, 22B")
                    .laboratories(new HashSet<>())
                    .hospitalPharmacies(new HashSet<>())
                    .hospitalWards(new HashSet<>())
                    .partnerPharmacies(new HashSet<>())
                    .doctors(new HashSet<>())
                    .build();

            dau = hospitalRepo.save(hospital);
        } else {
            dau = hospitalRepo.findByName(hospName1).orElseThrow(() -> new RuntimeException("Hospital not found"));
        }

        String hospName3 = "Med Life Hospital";
        Hospital mlh;
        if (!hospitalRepo.existsByName(hospName3)) {
            Hospital hospital = Hospital.builder()
                    .name(hospName3)
                    .address("Manas, 22B")
                    .laboratories(new HashSet<>())
                    .hospitalPharmacies(new HashSet<>())
                    .hospitalWards(new HashSet<>())
                    .partnerPharmacies(new HashSet<>())
                    .doctors(new HashSet<>())
                    .build();

            mlh = hospitalRepo.save(hospital);
        } else {
            mlh = hospitalRepo.findByName(hospName1).orElseThrow(() -> new RuntimeException("Hospital not found"));
        }

        String hospName4 = "AmirMed";
        Hospital am;
        if (!hospitalRepo.existsByName(hospName4)) {
            Hospital hospital = Hospital.builder()
                    .name(hospName4)
                    .address("Tunguch, 21A")
                    .laboratories(new HashSet<>())
                    .hospitalPharmacies(new HashSet<>())
                    .hospitalWards(new HashSet<>())
                    .partnerPharmacies(new HashSet<>())
                    .doctors(new HashSet<>())
                    .build();

            am = hospitalRepo.save(hospital);
        } else {
            am = hospitalRepo.findByName(hospName1).orElseThrow(() -> new RuntimeException("Hospital not found"));
        }

        String hospName5 = "Hello Health";
        Hospital hh;
        if (!hospitalRepo.existsByName(hospName5)) {
            Hospital hospital = Hospital.builder()
                    .name(hospName5)
                    .address("Abdrahmanov, 77H")
                    .laboratories(new HashSet<>())
                    .hospitalPharmacies(new HashSet<>())
                    .hospitalWards(new HashSet<>())
                    .partnerPharmacies(new HashSet<>())
                    .doctors(new HashSet<>())
                    .build();

            hh = hospitalRepo.save(hospital);
        } else {
            hh = hospitalRepo.findByName(hospName1).orElseThrow(() -> new RuntimeException("Hospital not found"));
        }

        createHospitalWardIfNotExists(sau, "Ward 1", 2);
        createHospitalWardIfNotExists(sau, "Ward 2", 2);
        createHospitalWardIfNotExists(sau, "Ward 3", 3);

        createHospitalWardIfNotExists(mau, "Ward 1", 2);
        createHospitalWardIfNotExists(mau, "Ward 2", 2);
        createHospitalWardIfNotExists(mau, "Ward 3", 3);

        String username = "s26218";
        if (!userRepo.existsByUsername(username)) {
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setName("Tagir");
            registerDTO.setSurname("Bektenov");
            registerDTO.setUsername(username);
            registerDTO.setPassword("123456");
            registerDTO.setEmail("example@example.com");
            registerDTO.setPhoneNumber("660-123-1732");
            registerDTO.setBirthdate(LocalDate.of(2004, 4, 11));
            registerDTO.setPesel("94111315115");
            registerDTO.setSex(Sex.MALE);

            registerDTO.setSsn("123456789");
            registerDTO.setHospitalId(1L);
            registerDTO.setSpecialization(Specialization.VETERINARIAN);
            registerDTO.setIsNurse(true);

            userService.registerUser(registerDTO);
        }

        String username1 = "s26219";
        if (!userRepo.existsByUsername(username1)) {
            RegisterDTO registerDTO1 = new RegisterDTO();
            registerDTO1.setName("Alice");
            registerDTO1.setSurname("Johnson");
            registerDTO1.setUsername(username1);
            registerDTO1.setPassword("alice123");
            registerDTO1.setEmail("alice.johnson@example.com");
            registerDTO1.setPhoneNumber("123-456-7890");
            registerDTO1.setBirthdate(LocalDate.of(1992, 5, 15));
            registerDTO1.setPesel("94111315111");
            registerDTO1.setSex(Sex.FEMALE);

            registerDTO1.setSsn("987654321");
            registerDTO1.setHospitalId(1L);
            registerDTO1.setSpecialization(Specialization.DENTIST);
            registerDTO1.setIsNurse(false);

            userService.registerUser(registerDTO1);
        }

        String username2 = "s26220";
        if (!userRepo.existsByUsername(username2)) {
            RegisterDTO registerDTO2 = new RegisterDTO();
            registerDTO2.setName("Bob");
            registerDTO2.setSurname("Smith");
            registerDTO2.setUsername(username2);
            registerDTO2.setPassword("bob123");
            registerDTO2.setEmail("bob.smith@example.com");
            registerDTO2.setPhoneNumber("234-567-8901");
            registerDTO2.setBirthdate(LocalDate.of(1988, 8, 20));
            registerDTO2.setPesel("94111315112");
            registerDTO2.setSex(Sex.MALE);

            registerDTO2.setSsn("654321987");
            registerDTO2.setHospitalId(2L);
            registerDTO2.setSpecialization(Specialization.PSYCHOLOGIST);
            registerDTO2.setIsNurse(true);

            userService.registerUser(registerDTO2);
        }

        String username3 = "s26221";
        if (!userRepo.existsByUsername(username3)) {
            RegisterDTO registerDTO3 = new RegisterDTO();
            registerDTO3.setName("Carol");
            registerDTO3.setSurname("Williams");
            registerDTO3.setUsername(username3);
            registerDTO3.setPassword("carol123");
            registerDTO3.setEmail("carol.williams@example.com");
            registerDTO3.setPhoneNumber("345-678-9012");
            registerDTO3.setBirthdate(LocalDate.of(1985, 3, 10));
            registerDTO3.setPesel("94111315113");
            registerDTO3.setSex(Sex.FEMALE);

            registerDTO3.setHospitalId(2L);
            registerDTO3.setSpecialization(Specialization.OPHTHALMOLOGIST);
            registerDTO3.setIsNurse(false);

            userService.registerUser(registerDTO3);
        }

        String username4 = "s26222";
        if (!userRepo.existsByUsername(username4)) {
            RegisterDTO registerDTO4 = new RegisterDTO();
            registerDTO4.setName("David");
            registerDTO4.setSurname("Brown");
            registerDTO4.setUsername(username4);
            registerDTO4.setPassword("david123");
            registerDTO4.setEmail("david.brown@example.com");
            registerDTO4.setPhoneNumber("456-789-0123");
            registerDTO4.setBirthdate(LocalDate.of(1979, 12, 25));
            registerDTO4.setPesel("94111315114");
            registerDTO4.setSex(Sex.MALE);

            registerDTO4.setSsn("789123654");
            registerDTO4.setHospitalId(2L);
            registerDTO4.setSpecialization(Specialization.VETERINARIAN);
            registerDTO4.setIsNurse(false);

            userService.registerUser(registerDTO4);
        }

        // TODO: only one partner can be added

        String address = "123 Main St";
        String company = "PharmaCorp";
        if (!privatePharmacyRepo.existsByAddressAndPharmaCompany(address, company)) {
            PrivatePharmacy privatePharmacy = PrivatePharmacy.builder()
                    .name("CityHealth")
                    .isCompoundPharmacy(true)
                    .address(address)
                    .pharmaCompany(company)
                    .build();

            sau.addPartnerPharmacy(privatePharmacy);

            privatePharmacyRepo.save(privatePharmacy);
        }

        String address1 = "234 Paper St";
        if (!privatePharmacyRepo.existsByAddressAndPharmaCompany(address1, company)) {
            PrivatePharmacy privatePharmacy = PrivatePharmacy.builder()
                    .name("MedLife")
                    .isCompoundPharmacy(false)
                    .address(address1)
                    .pharmaCompany(company)
                    .build();

            mau.addPartnerPharmacy(privatePharmacy);

            privatePharmacyRepo.save(privatePharmacy);
        }

        String address2 = "235 Paper St";
        String company1 = "AmirMed";
        if (!privatePharmacyRepo.existsByAddressAndPharmaCompany(address2, company1)) {
            PrivatePharmacy privatePharmacy = PrivatePharmacy.builder()
                    .name("AmirMed")
                    .isCompoundPharmacy(false)
                    .address(address2)
                    .pharmaCompany(company1)
                    .build();

            sau.addPartnerPharmacy(privatePharmacy);
            privatePharmacyRepo.save(privatePharmacy);
        }

        String hpName = "hp1";
        if (!hospitalPharmacyRepo.existsByHospitalIdAndName(sau.getId(), hpName)) {
            HospitalPharmacy hospitalPharmacy = HospitalPharmacy.builder()
                    .name(hpName)
                    .isCompoundPharmacy(false)
                    .hospital(sau)
                    .build();

            hospitalPharmacyRepo.save(hospitalPharmacy);
        }

        String hpName1 = "hp2";
        if (!hospitalPharmacyRepo.existsByHospitalIdAndName(sau.getId(), hpName1)) {
            HospitalPharmacy hospitalPharmacy = HospitalPharmacy.builder()
                    .name(hpName1)
                    .isCompoundPharmacy(false)
                    .hospital(sau)
                    .build();

            hospitalPharmacyRepo.save(hospitalPharmacy);
        }

        String hpName2 = "hp3";
        if (!hospitalPharmacyRepo.existsByHospitalIdAndName(sau.getId(), hpName2)) {
            HospitalPharmacy hospitalPharmacy = HospitalPharmacy.builder()
                    .name(hpName2)
                    .isCompoundPharmacy(true)
                    .hospital(sau)
                    .build();

            hospitalPharmacyRepo.save(hospitalPharmacy);
        }

        String hpName3 = "hp1";
        if (!hospitalPharmacyRepo.existsByHospitalIdAndName(mau.getId(), hpName3)) {
            HospitalPharmacy hospitalPharmacy = HospitalPharmacy.builder()
                    .name(hpName3)
                    .isCompoundPharmacy(true)
                    .hospital(mau)
                    .build();

            hospitalPharmacyRepo.save(hospitalPharmacy);
        }

        String hpName4 = "hp2";
        if (!hospitalPharmacyRepo.existsByHospitalIdAndName(mau.getId(), hpName4)) {
            HospitalPharmacy hospitalPharmacy = HospitalPharmacy.builder()
                    .name(hpName4)
                    .isCompoundPharmacy(false)
                    .hospital(mau)
                    .build();

            hospitalPharmacyRepo.save(hospitalPharmacy);
        }

        if (!laboratoryRepo.existsByHospitalIdAndFloor(sau.getId(), 1)) {
            Laboratory laboratory = Laboratory.builder()
                    .floor(1)
                    .hospital(sau)
                    .build();

            laboratoryRepo.save(laboratory);
        }

        if (!laboratoryRepo.existsByHospitalIdAndFloor(sau.getId(), 2)) {
            Laboratory laboratory = Laboratory.builder()
                    .floor(2)
                    .hospital(sau)
                    .build();

            laboratoryRepo.save(laboratory);
        }

        if (!laboratoryRepo.existsByHospitalIdAndFloor(mau.getId(), 2)) {
            Laboratory laboratory = Laboratory.builder()
                    .floor(2)
                    .hospital(mau)
                    .build();

            laboratoryRepo.save(laboratory);
        }

        Patient patient = patientRepo.findById(1L).orElseThrow(() -> new RuntimeException("Patient not found"));
        Patient patient1 = patientRepo.findById(3L).orElseThrow(() -> new RuntimeException("Patient not found"));
        Patient patient2 = patientRepo.findById(2L).orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepo.findById(2L).orElseThrow(() -> new RuntimeException("Doctor not found"));
        Doctor doctor1 = doctorRepo.findById(3L).orElseThrow(() -> new RuntimeException("Doctor not found"));

        createAppointments(patient, doctor);
        createAppointments(patient2, doctor1);
        createAppointments(patient1, doctor);
    }

    private void createAppointments(Patient patient, Doctor doctor) {
        if (checkPatientAndDoctorNotSamePerson(patient, doctor)) {
            if (!appointmentRepo.existsByPatientIdAndDoctorId(patient.getId(), doctor.getId())) {
                Appointment appointment = Appointment.builder()
                        .date(LocalDate.now())
                        .appointmentStatus(AppointmentStatus.UPCOMING)
                        .patient(patient)
                        .doctor(doctor)
                        .build();

                appointmentRepo.save(appointment);

                Appointment appointment1 = Appointment.builder()
                        .date(LocalDate.now())
                        .appointmentStatus(AppointmentStatus.ARCHIVED)
                        .patient(patient)
                        .doctor(doctor)
                        .build();

                appointmentRepo.save(appointment1);

                Appointment appointment2 = Appointment.builder()
                        .date(LocalDate.now())
                        .appointmentStatus(AppointmentStatus.UPCOMING)
                        .patient(patient)
                        .doctor(doctor)
                        .build();

                appointmentRepo.save(appointment2);
            }
        } else {
            throw new InvalidArgumentsException("Patient and Doctor are the same person");
        }

//        Nurse nurse = nurseRepo.findById(1L).orElseThrow(() -> new RuntimeException("Nurse not found"));
//        HospitalWard ward = hospitalWardRepo.findById(1L).orElseThrow(() -> new RuntimeException("Hospital ward not found"));
//        if (!stayingPatientRepo.existsById(patient.getId())) {
//            createHospitalization(patient, ward, nurse);
//        }
    }

    private void createHospitalWardIfNotExists(Hospital hospital, String wardNum, int capacity) {
        if (!hospitalWardRepo.existsByWardNumAndHospital(wardNum, hospital)) {
            HospitalWard hospitalWard = HospitalWard.builder()
                    .hospital(hospital)
                    .wardNum(wardNum)
                    .capacity(capacity)
                    .build();
            hospitalWardRepo.save(hospitalWard);
        }
    }

    private boolean checkPatientAndDoctorNotSamePerson(Patient patient, Doctor doctor) {
        return !Objects.equals(patient.getId(), doctor.getId());
    }

    private void createHospitalization(Patient patient, HospitalWard ward, Nurse nurse) {
        StayingPatient stayingPatient = new StayingPatient();

        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setHospitalWard(ward);
        hospitalization.setPatient(stayingPatient);
        hospitalization.addNurse(nurse);
        stayingPatient.setPatient(patient);

        TreatmentTracker treatmentTracker = new TreatmentTracker();
        treatmentTracker.setGotTreatmentToday(true);
        treatmentTracker.setPatient(stayingPatient);
        stayingPatient.setTreatmentTracker(treatmentTracker);

        stayingPatientRepo.save(stayingPatient);
    }
}