package tbektenov.com.sau;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tbektenov.com.sau.dtos.left_patient.ChangeToLeftPatientDTO;
import tbektenov.com.sau.dtos.staying_patient.ChangeToStayingPatientDTO;
import tbektenov.com.sau.dtos.user.RegisterDTO;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Appointment;
import tbektenov.com.sau.models.AppointmentStatus;
import tbektenov.com.sau.models.OrderEntity;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.pharmacy.HospitalPharmacy;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;
import tbektenov.com.sau.models.user.Sex;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.userRoles.Specialization;
import tbektenov.com.sau.repositories.*;
import tbektenov.com.sau.services.implementation.PatientServiceImpl;
import tbektenov.com.sau.services.implementation.UserServiceImpl;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Initializes the application data by populating the database with initial values.
 * This component is triggered when the application context is refreshed.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private StayingPatientRepo stayingPatientRepo;
    @Autowired
    private NurseRepo nurseRepo;
    @Autowired
    private LeftPatientRepo leftPatientRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private HospitalPharmacyRepo hospitalPharmacyRepo;
    @Autowired
    private HospitalRepo hospitalRepo;
    @Autowired
    private HospitalWardRepo hospitalWardRepo;
    @Autowired
    private LaboratoryRepo laboratoryRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PrivatePharmacyRepo privatePharmacyRepo;
    @Autowired
    private PatientServiceImpl patientService;
    @Autowired
    private HospitalizationRepo hospitalizationRepo;

    /**
     * Event handler that initializes data when the application context is refreshed.
     * This method creates hospitals, hospital wards, users, private pharmacies, and other related entities.
     * It also adds default data for appointments and other necessary records.
     *
     * @param event the event containing the application context refresh information
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    /**
     * Initializes the data in the database by creating and saving default values for hospitals,
     * hospital wards, users, private pharmacies, and other related entities.
     */
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
            registerDTO1.setIsNurse(true);

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

        String username5 = "s26223";
        if (!userRepo.existsByUsername(username5)) {
            RegisterDTO registerDTO4 = new RegisterDTO();
            registerDTO4.setName("David");
            registerDTO4.setSurname("Brown");
            registerDTO4.setUsername(username5);
            registerDTO4.setPassword("david123");
            registerDTO4.setEmail("david.brown@example.com");
            registerDTO4.setPhoneNumber("456-789-0123");
            registerDTO4.setBirthdate(LocalDate.of(1979, 12, 25));
            registerDTO4.setPesel("94111315124");
            registerDTO4.setSex(Sex.MALE);

            registerDTO4.setSsn("789123655");
            registerDTO4.setHospitalId(3L);
            registerDTO4.setSpecialization(Specialization.VETERINARIAN);
            registerDTO4.setIsNurse(false);

            userService.registerUser(registerDTO4);
        }

        String address = "Paper Str 123";
        String company = "Med4Life";

        if (!privatePharmacyRepo.existsByAddressAndPharmaCompany(address, company)) {
            PrivatePharmacy privatePharmacy = PrivatePharmacy.builder()
                    .isCompoundPharmacy(true)
                    .address(address)
                    .pharmaCompany(company)
                    .name("xyz")
                    .build();


            PrivatePharmacy res = privatePharmacyRepo.save(privatePharmacy);
            res.addPartnerHospital(mau);
            privatePharmacyRepo.save(res);
        }

        String address1 = "Paper Str 321";
        if (!privatePharmacyRepo.existsByAddressAndPharmaCompany(address1, company)) {
            PrivatePharmacy privatePharmacy = PrivatePharmacy.builder()
                    .isCompoundPharmacy(false)
                    .address(address1)
                    .pharmaCompany(company)
                    .name("xyz")
                    .build();

            PrivatePharmacy res = privatePharmacyRepo.save(privatePharmacy);
            res.addPartnerHospital(mau);
            res.addPartnerHospital(sau);
            privatePharmacyRepo.save(res);
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

        Nurse nurse = nurseRepo.findById(3L).orElseThrow(() -> new RuntimeException("Nurse not found"));
        HospitalWard ward = hospitalWardRepo.findById(1L).orElseThrow(() -> new RuntimeException("Hospital ward not found"));

        if (!stayingPatientRepo.existsById(patient.getId())) {
            createHospitalization(patient, ward, nurse);
        }

        if (!stayingPatientRepo.existsById(patient2.getId())) {
            createHospitalization(patient2, ward, nurse);
        }

        if (!leftPatientRepo.existsById(patient.getId())) {
            ChangeToLeftPatientDTO changeToLeftPatientDTO = new ChangeToLeftPatientDTO();

            changeToLeftPatientDTO.setConclusion("Is healthy");

            patientService.changeToLeftPatient(patient.getId(), changeToLeftPatientDTO);
        }

        HospitalPharmacy hp1 = hospitalPharmacyRepo.findByNameAndHospitalId("hp1", sau.getId())
                .orElseThrow(
                        () -> new ObjectNotFoundException("Hospital pharmacy not found")
                );

        HospitalPharmacy hp2 = hospitalPharmacyRepo.findByNameAndHospitalId("hp2", sau.getId())
                .orElseThrow(
                        () -> new ObjectNotFoundException("Hospital pharmacy not found")
                );

        if (!orderRepo.existsByDoctorId(doctor.getId())) {
            OrderEntity order1 = doctor.sendOrderToHospPharmacy(hp1, "Something");
            OrderEntity order2 = doctor.sendOrderToHospPharmacy(hp1, "Something1");
            OrderEntity order3 = doctor.sendOrderToHospPharmacy(hp1, "Something2");
            OrderEntity order4 = doctor.sendOrderToHospPharmacy(hp2, "Something");

            orderRepo.save(order1);
            orderRepo.save(order2);
            orderRepo.save(order3);
            orderRepo.save(order4);

            hospitalPharmacyRepo.save(hp1);
            hospitalPharmacyRepo.save(hp2);

            OrderEntity finishedOrder = hp1.finishOrder(order1);
            orderRepo.save(finishedOrder);
        }

    }

    private void createAppointments(Patient patient, Doctor doctor) {
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

    private void createHospitalization(Patient patient, HospitalWard ward, Nurse nurse) {
        ChangeToStayingPatientDTO changeToStayingPatientDTO = new ChangeToStayingPatientDTO();

        changeToStayingPatientDTO.setHospitalId(ward.getHospital().getId());
        changeToStayingPatientDTO.setWardNum(ward.getWardNum());
        changeToStayingPatientDTO.setNurseId(nurse.getId());

        patientService.changeToStayingPatient(patient.getId(), changeToStayingPatientDTO);
    }
}