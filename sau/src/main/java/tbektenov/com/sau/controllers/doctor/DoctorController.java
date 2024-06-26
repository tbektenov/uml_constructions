package tbektenov.com.sau.controllers.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.doctor.CreateDoctorDTO;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.dtos.doctor.DoctorResponse;
import tbektenov.com.sau.services.IDoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/")
public class DoctorController {

    private IDoctorService doctorService;

    @Autowired
    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Retrieves a paginated list of doctors.
     *
     * @param pageNo the page number to retrieve, defaults to 0
     * @param pageSize the number of doctors per page, defaults to 10
     * @return a paginated list of doctors wrapped in a DoctorResponse
     */
    @GetMapping("doctors")
    public ResponseEntity<DoctorResponse> getDoctors(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(doctorService.getAllDoctors(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("doctors/hospital/{id}")
    public ResponseEntity<List<DoctorDTO>> getDoctors(
            @PathVariable("id") Long id
    ) {
        return new ResponseEntity<>(doctorService.getDoctorsFromHospitalById(id), HttpStatus.OK);
    }

    /**
     * Assigns a doctor role to a user and creates a new doctor record.
     *
     * @param createDoctorDTO the data transfer object containing doctor details
     * @return the created doctor details
     */
    @PostMapping("/giveDoctorRole")
    public ResponseEntity<String> createDoctor(
            @RequestBody CreateDoctorDTO createDoctorDTO
            ) {
        return new ResponseEntity<>(doctorService.createDoctor(createDoctorDTO), HttpStatus.CREATED);
    }

    @PutMapping("{doctorId}/finishAppointment/{appointId}")
    public ResponseEntity<String> finishAppointment(
            @PathVariable Long doctorId,
            @PathVariable Long appointId
    ) {
        doctorService.finishAppointment(doctorId, appointId);
        return new ResponseEntity<>(String.format("Appointment: %d was finished and archived", appointId), HttpStatus.OK);
    }

    @PutMapping("doctors/{doctor_id}/hospital/{hospital_id}")
    public ResponseEntity<DoctorDTO> getDoctors(
            @PathVariable("doctor_id") Long doctor_id,
            @PathVariable("hospital_id") Long hospital_id
    ) {
        return new ResponseEntity<>(doctorService.assignDoctorToAnotherHospital(doctor_id, hospital_id), HttpStatus.OK);
    }
}
