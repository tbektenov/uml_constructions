package tbektenov.com.sau.controllers.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.doctor.CreateDoctorDTO;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.dtos.order.CreateOrderDTO;
import tbektenov.com.sau.services.IDoctorService;

import java.util.List;

/**
 * Controller class that handles requests related to doctors.
 */
@Controller
@RequestMapping("/api/doctor/")
public class DoctorController {

    private final IDoctorService doctorService;

    /**
     * Constructs a {@code DoctorController} with the specified {@code IDoctorService}.
     *
     * @param doctorService the service handling doctor-related operations
     */
    @Autowired
    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Retrieves a paginated list of doctors.
     *
     * @param pageNo the page number to retrieve, defaults to 0
     * @param pageSize the number of doctors per page, defaults to 5
     * @return a list of doctors wrapped in a {@link ResponseEntity}
     */
    @GetMapping("doctors")
    public ResponseEntity<List<DoctorDTO>> getDoctors(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    /**
     * Retrieves a list of doctors from a specific hospital by the hospital's ID.
     *
     * @param id the ID of the hospital
     * @return a list of doctors associated with the specified hospital wrapped in a {@link ResponseEntity}
     */
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
     * @return a confirmation message wrapped in a {@link ResponseEntity}
     */
    @PostMapping("/giveDoctorRole")
    public ResponseEntity<String> createDoctor(
            @RequestBody CreateDoctorDTO createDoctorDTO
    ) {
        return new ResponseEntity<>(doctorService.createDoctor(createDoctorDTO), HttpStatus.CREATED);
    }

    /**
     * Marks an appointment as finished and archives it.
     *
     * @param doctorId the ID of the doctor finishing the appointment
     * @param appointId the ID of the appointment to finish
     * @return a confirmation message wrapped in a {@link ResponseEntity}
     */
    @PutMapping("{doctorId}/finishAppointment/{appointId}")
    public ResponseEntity<String> finishAppointment(
            @PathVariable Long doctorId,
            @PathVariable Long appointId
    ) {
        doctorService.finishAppointment(doctorId, appointId);
        return new ResponseEntity<>(String.format("Appointment: %d was finished and archived", appointId), HttpStatus.OK);
    }

    /**
     * Creates a new order for a doctor within a hospital pharmacy.
     *
     * @param doctorId the ID of the doctor placing the order
     * @param hospitalPharmacyId the ID of the hospital pharmacy
     * @param createOrderDTO the data transfer object containing order details
     * @return a confirmation message wrapped in a {@link ResponseEntity}
     */
    @PostMapping("order/{doctorId}/{hospitalPharmacyId}")
    public ResponseEntity<String> createOrder(
            @PathVariable Long doctorId,
            @PathVariable Long hospitalPharmacyId,
            @RequestBody CreateOrderDTO createOrderDTO
    ) {
        doctorService.createOrder(doctorId, hospitalPharmacyId, createOrderDTO);
        return new ResponseEntity<>("Order successfully created", HttpStatus.CREATED);
    }

    /**
     * Assigns a doctor to another hospital.
     *
     * @param doctor_id the ID of the doctor to reassign
     * @param hospital_id the ID of the hospital to assign the doctor to
     * @return the updated doctor details wrapped in a {@link ResponseEntity}
     */
    @PutMapping("doctors/{doctor_id}/hospital/{hospital_id}")
    public ResponseEntity<DoctorDTO> getDoctors(
            @PathVariable("doctor_id") Long doctor_id,
            @PathVariable("hospital_id") Long hospital_id
    ) {
        return new ResponseEntity<>(doctorService.assignDoctorToAnotherHospital(doctor_id, hospital_id), HttpStatus.OK);
    }

    /**
     * Deletes a doctor by their ID.
     *
     * @param doctor_id the ID of the doctor to delete
     * @return a confirmation message wrapped in a {@link ResponseEntity}
     */
    @DeleteMapping("/doctor/{doctor_id}")
    public ResponseEntity<String> deleteDoctor(
            @PathVariable Long doctor_id
    ) {
        doctorService.deleteDoctor(doctor_id);
        return new ResponseEntity<>(String.format("Doctor: %d was deleted", doctor_id), HttpStatus.OK);
    }
}
