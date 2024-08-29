package tbektenov.com.sau;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tbektenov.com.sau.exceptions.InvalidArgumentsException;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.hospital.Laboratory;
import tbektenov.com.sau.models.pharmacy.PrivatePharmacy;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.userRoles.Doctor;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.Sex;
import tbektenov.com.sau.repositories.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SauApplicationTests {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PatientRepo patientRepo;
	@Autowired
	HospitalizationRepo hospitalizationRepo;
    @Autowired
    private HospitalWardRepo hospitalWardRepo;
    @Autowired
    private NurseRepo nurseRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private StayingPatientRepo stayingPatientRepo;
    @Autowired
    private LaboratoryRepo laboratoryRepo;
    @Autowired
    private HospitalRepo hospitalRepo;
    @Autowired
    private PrivatePharmacyRepo privatePharmacyRepo;


	@Test
	@Transactional
	void creatingUserWithBothPatientTypes() {
		UserEntity user = new UserEntity();
		user.setName("John");
		user.setSurname("Doe");
		user.setUsername("johndoe");
		user.setPassword("password");
		user.setEmail("johndoe@example.com");
		user.setPhoneNumber("123-456-7890");
		user.setBirthdate(LocalDate.of(1985, 5, 15));
		user.setPesel("94111315129");
		user.setSex(Sex.MALE);

		Patient patient = new Patient();
		patient.setUser(user);
		patient.setSsn("123459876");

		StayingPatient stayingPatient = new StayingPatient();
		stayingPatient.setPatient(patient);
		patient.setStayingPatient(stayingPatient);

		LeftPatient leftPatient = new LeftPatient();
		leftPatient.setPatient(patient);
		patient.setLeftPatient(leftPatient);

		patientRepo.save(patient);

		assertThrows(ConstraintViolationException.class, () -> {
			patientRepo.flush();
		});
	}

	@Test
	@Transactional
	void assignSeveralNursesToHospitalizationAndRemoveOne() {
		HospitalWard hospitalWard = hospitalWardRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such ward.")
		);

		UserEntity user = new UserEntity();
		user.setName("John");
		user.setSurname("Doe");
		user.setUsername("johndoe");
		user.setPassword("password");
		user.setEmail("johndoe@example.com");
		user.setPhoneNumber("123-456-7890");
		user.setBirthdate(LocalDate.of(1985, 5, 15));
		user.setPesel("94111315129");
		user.setSex(Sex.MALE);

		Patient patient = new Patient();
		patient.setUser(user);
		patient.setSsn("123456987");

		StayingPatient stayingPatient = new StayingPatient();
		stayingPatient.setPatient(patient);
		patient.setStayingPatient(stayingPatient);

		patientRepo.save(patient);

		Nurse nurse = nurseRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such nurse.")
		);

		Set<Nurse> nurses = new HashSet<>();
		nurses.add(nurse);

		Hospitalization hospitalization = Hospitalization.builder()
				.hospitalWard(hospitalWard)
				.patient(stayingPatient)
				.nurses(nurses)
				.build();

		hospitalizationRepo.save(hospitalization);

		Nurse nurse1 = nurseRepo.findById(2L).orElseThrow(
				() -> new ObjectNotFoundException("No such nurse.")
		);

		nurse1.addHospitalization(hospitalization);

		Hospitalization result = hospitalizationRepo.save(hospitalization);

		assertEquals(2, result.getNurses().size());

		nurse1.removeHospitalization(hospitalization);
		hospitalizationRepo.save(hospitalization);

		assertEquals(1, result.getNurses().size());
	}

	@Test
	@Transactional
	public void doctorAssignNurseToHospitalization() {
		HospitalWard hospitalWard = hospitalWardRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such ward.")
		);

		Patient patient = patientRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such user.")
		);

		StayingPatient stayingPatient = StayingPatient.builder()
				.patient(patient)
				.build();

		patient.setStayingPatient(stayingPatient);

		Nurse nurse = nurseRepo.findById(2L).orElseThrow(
				() -> new ObjectNotFoundException("No such nurse.")
		);

		Set<Nurse> nurses = new HashSet<>();
		nurses.add(nurse);

		Hospitalization hospitalization = Hospitalization.builder()
				.hospitalWard(hospitalWard)
				.patient(stayingPatient)
				.nurses(nurses)
				.build();

		hospitalizationRepo.save(hospitalization);

		Doctor doctor = doctorRepo.findById(1L).orElseThrow(
				()	-> new ObjectNotFoundException("No such doctor.")
		);

		Nurse nurse1 = nurseRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such nurse.")
		);

		doctor.assignNurseToHospitalization(nurse1, hospitalization);

		assertEquals(2, nurse1.getHospitalizations().size());

		assertThrows(InvalidArgumentsException.class, () -> {
			doctor.assignNurseToHospitalization(nurse1, hospitalization);
		});
	}

	@Test
	@Transactional
	public void getAssignedPatients () {
		HospitalWard hospitalWard = hospitalWardRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such ward.")
		);

		Patient patient = patientRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such user.")
		);

		StayingPatient stayingPatient = StayingPatient.builder()
				.patient(patient)
				.build();

		patient.setStayingPatient(stayingPatient);

		Nurse nurse = nurseRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such nurse.")
		);

		Set<Nurse> nurses = new HashSet<>();
		nurses.add(nurse);

		Hospitalization hospitalization = Hospitalization.builder()
				.hospitalWard(hospitalWard)
				.patient(stayingPatient)
				.nurses(nurses)
				.build();

		assertEquals(2, nurse.getAssignedPatients().size());
		nurse.getAssignedWards().forEach(System.out::println);
	}

	@Test
	@Transactional
	public void tryingAssignNurseToItselfThrowsException () {
		HospitalWard hospitalWard = hospitalWardRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such ward.")
		);

		Patient patient = patientRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such user.")
		);

		StayingPatient stayingPatient = StayingPatient.builder()
				.patient(patient)
				.build();

		patient.setStayingPatient(stayingPatient);

		Nurse nurse = nurseRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such nurse.")
		);

		Set<Nurse> nurses = new HashSet<>();
		nurses.add(nurse);

		Hospitalization hospitalization = Hospitalization.builder()
				.hospitalWard(hospitalWard)
				.patient(stayingPatient)
				.nurses(nurses)
				.build();

		assertThrows(ConstraintViolationException.class, () -> {
			hospitalizationRepo.save(hospitalization);
		});
	}

	@Test
	@Transactional
	public void assignDoctorToLab() {
		Laboratory laboratory = laboratoryRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such lab.")
		);

		Laboratory laboratory1 = laboratoryRepo.findById(2L).orElseThrow(
				() -> new ObjectNotFoundException("No such lab.")
		);

		Doctor doctor = doctorRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such doctor.")
		);

		Doctor doctor1 = doctorRepo.findById(2L).orElseThrow(
				() -> new ObjectNotFoundException("No such doctor.")
		);

		laboratory.addDoctor(doctor);
		doctor1.setLaboratory(laboratory);

		assertNotNull(doctor.getLaboratory());
		assertNotNull(doctor1.getLaboratory());
		assertEquals(2, laboratory.getDoctors().size());

		laboratory1.addDoctor(doctor);
		assertEquals(1, laboratory.getDoctors().size());
		assertEquals(1, laboratory1.getDoctors().size());

		laboratoryRepo.save(laboratory);

		doctor1.setLaboratory(null);
		laboratory1.removeDoctor(doctor);
		assertEquals(0, laboratory.getDoctors().size());
		assertEquals(0, laboratory1.getDoctors().size());
	}

	@Test
	@Transactional
	public void assignDoctorToLabFromDiffHospitalThrowsException() {
		Laboratory laboratory = laboratoryRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such lab.")
		);

		Doctor doctor = doctorRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such doctor.")
		);

		assertThrows(InvalidArgumentsException.class, () -> {
			laboratory.addDoctor(doctor);
		});
	}

	@Test
	@Transactional
	public void addPartnerHospital() {
		Hospital hospital = hospitalRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such hospital")
		);

		Hospital hospital1 = hospitalRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such hospital")
		);

		PrivatePharmacy privatePharmacy = privatePharmacyRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such privatePharmacy")
		);

		privatePharmacy.addPartnerHospital(hospital);
		privatePharmacy.addPartnerHospital(hospital1);

		assertEquals(3, privatePharmacy.getPartnerHospitals().size());
		assertEquals(2, hospital.getPartnerPharmacies().size());
		assertEquals(1, hospital1.getPartnerPharmacies().size());
	}

	@Test
	@Transactional
	public void createHospitalizationWithNoNursesThrowsException() {
		HospitalWard hospitalWard = hospitalWardRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such ward.")
		);

		Patient patient = patientRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such user.")
		);

		StayingPatient stayingPatient = StayingPatient.builder()
				.patient(patient)
				.build();

		patient.setStayingPatient(stayingPatient);

		Set<Nurse> nurses = new HashSet<>();

		Hospitalization hospitalization = Hospitalization.builder()
				.hospitalWard(hospitalWard)
				.patient(stayingPatient)
				.nurses(nurses)
				.build();

		assertThrows(ConstraintViolationException.class, () -> {
			hospitalizationRepo.save(hospitalization);
		});
	}

	@Test
	@Transactional
	public void assignStayingPatientRole() {
		Patient patient = patientRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such patient.")
		);

		HospitalWard hospitalWard = hospitalWardRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such hospital ward.")
		);

		Set<Nurse> nurses = new HashSet<>(nurseRepo.findAll());
		if (nurses.isEmpty()) {
			throw new IllegalArgumentException("No nurses available.");
		}

		patient.createAndAssignStayingPatient(hospitalWard, nurses);

		assertNotNull(patient.getStayingPatient());
		assertNull(patient.getLeftPatient());
		assertNotNull(patient.getStayingPatient().getHospitalization());
		assertEquals(hospitalWard, patient.getStayingPatient().getHospitalization().getHospitalWard());
		assertEquals(nurses, patient.getStayingPatient().getHospitalization().getNurses());
	}

	@Test
	@Transactional
	public void assignLeftPatientRole() {
		Patient patient = patientRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such patient.")
		);

		HospitalWard hospitalWard = hospitalWardRepo.findById(1L).orElseThrow(
				() -> new ObjectNotFoundException("No such hospital ward.")
		);

		Set<Nurse> nurses = new HashSet<>(nurseRepo.findAll());
		if (nurses.isEmpty()) {
			throw new IllegalArgumentException("No nurses available.");
		}

		patient.createAndAssignStayingPatient(hospitalWard, nurses);

		LeftPatient leftPatient = new LeftPatient();
		leftPatient.setPatient(patient);
		leftPatient.setConclusion("Patient has recovered.");

		patient.setLeftPatientRole(leftPatient);

		assertNotNull(patient.getLeftPatient());
		assertNull(patient.getStayingPatient());
		assertEquals(leftPatient, patient.getLeftPatient());
		assertEquals("Patient has recovered.", patient.getLeftPatient().getConclusion());
	}

}
