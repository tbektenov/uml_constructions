package tbektenov.com.sau.controllers.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.laboratory.CreateUpdateLaboratoryDTO;
import tbektenov.com.sau.dtos.laboratory.LaboratoryDTO;
import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.CreateUpdateHospitalPharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.hospitalPharmacy.HospitalPharmacyDTO;
import tbektenov.com.sau.services.IHospitalPharmacyService;

import java.util.List;

@Controller
@RequestMapping("/api/hp/")
public class HospitalPharmacyController {

    private IHospitalPharmacyService hospitalPharmacyService;

    @Autowired
    public HospitalPharmacyController(IHospitalPharmacyService hospitalPharmacyService) {
        this.hospitalPharmacyService = hospitalPharmacyService;
    }

    /**
     * Creates a new hospital pharmacy for the specified hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param createUpdateHospitalPharmacyDTO the DTO containing details of the new hospital pharmacy
     * @return the created hospital pharmacy details
     */
    @PostMapping("/create/{hospitalId}")
    public ResponseEntity<HospitalPharmacyDTO> createHospitalPharmacy(
            @PathVariable("hospitalId") Long hospitalId,
            @RequestBody CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO
    ) {
        return new ResponseEntity<>(hospitalPharmacyService.createHospitalPharmacy(hospitalId, createUpdateHospitalPharmacyDTO), HttpStatus.CREATED);
    }

    /**
     * Retrieves all hospital pharmacies for a specific hospital.
     *
     * @param hospitalId the ID of the hospital
     * @return a list of hospital pharmacies associated with the specified hospital
     */
    @GetMapping("/hospital/{hospitalId}/hospitalPharmacies")
    public ResponseEntity<List<HospitalPharmacyDTO>> getHospitalPharmaciesByHospitalId(@PathVariable("hospitalId") Long hospitalId) {
        return ResponseEntity.ok(hospitalPharmacyService.getHospitalPharmaciesByHospitalId(hospitalId));
    }

    /**
     * Retrieves the details of a specific hospital pharmacy by its ID within a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param hospitalPharmacyId the ID of the hospital pharmacy
     * @return the details of the specified hospital pharmacy
     */
    @GetMapping("/hospital/{hospitalId}/hospitalPharmacy/{hospitalPharmacyId}")
    public ResponseEntity<HospitalPharmacyDTO> getHospitalPharmacyById(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("hospitalPharmacyId") Long hospitalPharmacyId
    ) {
        HospitalPharmacyDTO hospitalPharmacyDTO = hospitalPharmacyService.getHospitalPharmacyById(hospitalId, hospitalPharmacyId);
        return ResponseEntity.ok(hospitalPharmacyDTO);
    }

    /**
     * Updates the details of a specific hospital pharmacy.
     *
     * @param hospitalId the ID of the hospital
     * @param hospitalPharmacyId the ID of the hospital pharmacy to update
     * @param createUpdateHospitalPharmacyDTO the DTO containing updated details of the hospital pharmacy
     * @return the updated hospital pharmacy details
     */
    @PutMapping("/hospital/{hospitalId}/hospitalPharmacy/{hospitalPharmacyId}")
    public ResponseEntity<HospitalPharmacyDTO> updateHospitalPharmacy(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("hospitalPharmacyId") Long hospitalPharmacyId,
            @RequestBody CreateUpdateHospitalPharmacyDTO createUpdateHospitalPharmacyDTO
    ) {
        HospitalPharmacyDTO hospitalPharmacyDTO = hospitalPharmacyService.updateHospitalPharmacy(hospitalId, hospitalPharmacyId, createUpdateHospitalPharmacyDTO);
        return ResponseEntity.ok(hospitalPharmacyDTO);
    }

    /**
     * Deletes a specific hospital pharmacy by its ID within a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param hospitalPharmacyId the ID of the hospital pharmacy to delete
     * @return a confirmation message of the deletion
     */
    @DeleteMapping("/hospital/{hospitalId}/hospitalPharmacy/{hospitalPharmacyId}")
    public ResponseEntity<String> deleteHospitalPharmacy(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("hospitalPharmacyId") Long hospitalPharmacyId
    ) {
        hospitalPharmacyService.deleteHospitalPharmacy(hospitalId, hospitalPharmacyId);
        return new ResponseEntity<>("Laboratory was deleted successfully", HttpStatus.OK);
    }
}
