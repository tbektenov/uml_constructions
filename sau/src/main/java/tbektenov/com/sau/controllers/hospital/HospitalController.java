package tbektenov.com.sau.controllers.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.hospital.CreateUpdateHospitalDTO;
import tbektenov.com.sau.dtos.hospital.HospitalAndDoctorsDTO;
import tbektenov.com.sau.dtos.hospital.HospitalDTO;
import tbektenov.com.sau.dtos.hospital.HospitalResponse;
import tbektenov.com.sau.services.IHospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/hospital/")
public class HospitalController {

    private IHospitalService hospitalService;

    @Autowired
    public HospitalController(IHospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    /**
     * Retrieves a paginated list of hospitals.
     *
     * @param pageNo the page number to retrieve, defaults to 0
     * @param pageSize the number of hospitals per page, defaults to 10
     * @return a paginated list of hospitals wrapped in a HospitalResponse
     */
    @GetMapping("hospitals")
    public ResponseEntity<HospitalResponse> getHospitals(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(hospitalService.getAllHospitals(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("hospitals/doctors")
    public ResponseEntity<List<HospitalAndDoctorsDTO>> getHospitals() {
        return new ResponseEntity<>(hospitalService.getAllHospitalsWithDoctors(), HttpStatus.OK);
    }

    /**
     * Creates a new hospital with the provided details.
     *
     * @param createUpdateHospitalDTO the data transfer object containing hospital details
     * @return the created hospital details
     */
    @PostMapping("hospital/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HospitalDTO> createHospital(@RequestBody CreateUpdateHospitalDTO createUpdateHospitalDTO) {
        return new ResponseEntity<>(hospitalService.createHospital(createUpdateHospitalDTO), HttpStatus.CREATED);
    }

    /**
     * Retrieves the details of a specific hospital by its ID.
     *
     * @param hospitalId the ID of the hospital to retrieve
     * @return the details of the specified hospital
     */
    @GetMapping("hospital/find/{id}")
    public ResponseEntity<HospitalDTO> hospitalDetail(@PathVariable("id") Long hospitalId) {
        return ResponseEntity.ok(hospitalService.getHospitalById(hospitalId));
    }

    /**
     * Updates the details of an existing hospital.
     *
     * @param createUpdateHospitalDTO the updated hospital details
     * @param hospitalId the ID of the hospital to update
     * @return the updated hospital details
     */
    @PutMapping("hospital/update/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(@RequestBody CreateUpdateHospitalDTO createUpdateHospitalDTO, @PathVariable("id") Long hospitalId) {
        HospitalDTO response = hospitalService.updateHospital(createUpdateHospitalDTO, hospitalId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a hospital by its ID.
     *
     * @param hospitalId the ID of the hospital to delete
     * @return a confirmation message of the deletion
     */
    @DeleteMapping("hospital/delete/{id}")
    public ResponseEntity<String> deleteHospital(@PathVariable("id") Long hospitalId) {
        hospitalService.deleteHospital(hospitalId);
        return new ResponseEntity<>("Hospital deleted", HttpStatus.OK);
    }

    /**
     * Adds a partner pharmacy to a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param pharmacyId the ID of the pharmacy to add as a partner
     * @return the updated hospital details with the new partner pharmacy
     */
    @PostMapping("{hospitalId}/partners/{pharmacyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HospitalDTO> addPartnerPharmacy(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("pharmacyId") Long pharmacyId
    ) {
        return new ResponseEntity<>(hospitalService.addPartnerPharmacy(hospitalId, pharmacyId), HttpStatus.OK);
    }

    /**
     * Removes a partner pharmacy from a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param pharmacyId the ID of the pharmacy to remove as a partner
     * @return the updated hospital details without the partner pharmacy
     */
    @DeleteMapping("{hospitalId}/partners/{pharmacyId}")
    public ResponseEntity<HospitalDTO> removePartnerPharmacy(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("pharmacyId") Long pharmacyId
    ) {
        return new ResponseEntity<>(hospitalService.removePartnerPharmacy(hospitalId, hospitalId), HttpStatus.OK);
    }

    /**
     * Associates a doctor with a hospital, effectively hiring them.
     *
     * @param hospitalId the ID of the hospital
     * @param doctorId the ID of the doctor to hire
     * @return the updated hospital details with the new doctor
     */
    @PostMapping("{hospitalId}/doctors/{doctorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HospitalDTO> hireDoctor(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("doctorId") Long doctorId
    ) {
        return new ResponseEntity<>(hospitalService.hireDoctor(hospitalId, doctorId), HttpStatus.OK);
    }

    /**
     * Removes the association of a doctor with a hospital, effectively firing them.
     *
     * @param hospitalId the ID of the hospital
     * @param doctorId the ID of the doctor to fire
     * @return the updated hospital details without the doctor
     */
    @DeleteMapping("{hospitalId}/doctors/{doctorId}")
    public ResponseEntity<HospitalDTO> fireDoctor(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("doctorId") Long doctorId
    ) {
        return new ResponseEntity<>(hospitalService.fireDoctor(hospitalId, doctorId), HttpStatus.OK);
    }
}
