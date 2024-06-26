package tbektenov.com.sau.controllers.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.patient.CreatePatientDTO;
import tbektenov.com.sau.dtos.patient.PatientDTO;
import tbektenov.com.sau.services.IPatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patient/")
public class PatientController {

    private IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Retrieves a list of all patients.
     *
     * @return a list of PatientDTO representing all patients
     */
    @GetMapping("patients")
    public ResponseEntity<List<PatientDTO>> getDoctors() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    /**
     * Assigns a patient role to a user and creates a new patient record.
     *
     * @param createPatientDTO the data transfer object containing patient details
     * @return the created patient details wrapped in a PatientDTO
     */
    @PostMapping("/givePatientRole")
    public ResponseEntity<PatientDTO> createDoctor(
            @RequestBody CreatePatientDTO createPatientDTO
    ) {
        return new ResponseEntity<>(patientService.createPatient(createPatientDTO), HttpStatus.CREATED);
    }
}
