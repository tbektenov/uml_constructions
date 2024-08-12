package tbektenov.com.sau.controllers.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.patient.CreatePatientDTO;
import tbektenov.com.sau.dtos.patient.PatientDTO;
import tbektenov.com.sau.dtos.staying_patient.ChangeToStayingPatientDTO;
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

    @GetMapping("{patientId}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long patientId) {
        return new ResponseEntity<>(patientService.getPatientById(patientId), HttpStatus.OK);
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

    /**
     * Endpoint to change a patient to StayingPatient.
     *
     * @param patientId The ID of the patient to change.
     * @return A response message indicating the result of the operation.
     */
    @PostMapping("changeToStayingPatient/{patientId}")
    public ResponseEntity<String> changeToStayingPatient(
            @PathVariable("patientId") Long patientId,
            @RequestBody ChangeToStayingPatientDTO changeToStayingPatientDTO) {
        String result = patientService.changeToStayingPatient(patientId, changeToStayingPatientDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Endpoint to change a patient to LeftPatient.
     *
     * @param patientId  The ID of the patient to change.
     * @param conclusion The conclusion or reason for leaving.
     * @return A response message indicating the result of the operation.
     */
    @PostMapping("changeToLeftPatient/{patientId}")
    public ResponseEntity<String> changeToLeftPatient(
            @PathVariable("patientId") Long patientId,
            @RequestBody String conclusion) {
        String result = patientService.changeToLeftPatient(patientId, conclusion);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
