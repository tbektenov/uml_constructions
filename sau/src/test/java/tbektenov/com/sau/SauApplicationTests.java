package tbektenov.com.sau;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tbektenov.com.sau.exceptions.ObjectNotFoundException;
import tbektenov.com.sau.models.Hospitalization;
import tbektenov.com.sau.models.hospital.HospitalWard;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.patientRoles.LeftPatient;
import tbektenov.com.sau.models.user.patientRoles.StayingPatient;
import tbektenov.com.sau.models.user.userRoles.Nurse;
import tbektenov.com.sau.models.user.userRoles.Patient;
import tbektenov.com.sau.models.user.Sex;
import tbektenov.com.sau.repositories.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

		userRepo.save(user);

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

		userRepo.save(user);

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

		Nurse nurse1 = nurseRepo.findById(3L).orElseThrow(
				() -> new ObjectNotFoundException("No such nurse.")
		);

		nurse1.addHospitalization(hospitalization);

		Hospitalization result = hospitalizationRepo.save(hospitalization);

		assertEquals(2, result.getNurses().size());

		nurse1.removeHospitalization(hospitalization);
		hospitalizationRepo.save(hospitalization);

		assertEquals(1, result.getNurses().size());
	}
}
