package tbektenov.com.sau.controllers.hospitalWard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.hospitalWard.CreateUpdateHospitalWardDTO;
import tbektenov.com.sau.dtos.hospitalWard.HospitalWardDTO;
import tbektenov.com.sau.services.IHospitalWardService;

import java.util.List;

/**
 * Controller class for managing hospital wards.
 */
@Controller
@RequestMapping("/api/ward/")
public class HospitalWardController {

    private final IHospitalWardService hospitalWardService;

    /**
     * Constructs a {@code HospitalWardController} with the specified {@code IHospitalWardService}.
     *
     * @param hospitalWardService the service handling hospital ward-related operations
     */
    @Autowired
    public HospitalWardController(IHospitalWardService hospitalWardService) {
        this.hospitalWardService = hospitalWardService;
    }

    /**
     * Creates a new hospital ward for the specified hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param createUpdateHospitalWardDTO the DTO containing details of the new ward
     * @return the created hospital ward details wrapped in a {@link ResponseEntity}
     */
    @PostMapping("/create/{hospitalId}")
    public ResponseEntity<HospitalWardDTO> createHospitalWard(
            @PathVariable("hospitalId") Long hospitalId,
            @RequestBody CreateUpdateHospitalWardDTO createUpdateHospitalWardDTO
    ) {
        HospitalWardDTO hospitalWardDTO = hospitalWardService.createHospitalWard(hospitalId, createUpdateHospitalWardDTO);
        return new ResponseEntity<>(hospitalWardDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves all wards for a specific hospital.
     *
     * @param hospitalId the ID of the hospital
     * @return a list of hospital wards associated with the specified hospital wrapped in a {@link ResponseEntity}
     */
    @GetMapping("/hospital/{hospitalId}/wards")
    public ResponseEntity<List<HospitalWardDTO>> getWardsByHospitalId(@PathVariable("hospitalId") Long hospitalId) {
        List<HospitalWardDTO> hospitalWards = hospitalWardService.getHospitalWardsByHospitalId(hospitalId);
        return ResponseEntity.ok(hospitalWards);
    }

    /**
     * Retrieves the details of a specific ward by its ID within a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param wardId the ID of the ward
     * @return the details of the specified hospital ward wrapped in a {@link ResponseEntity}
     */
    @GetMapping("/hospital/{hospitalId}/ward/{wardId}")
    public ResponseEntity<HospitalWardDTO> getWardById(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("wardId") Long wardId
    ) {
        HospitalWardDTO hospitalWardDTO = hospitalWardService.getHospitalWardById(hospitalId, wardId);
        return ResponseEntity.ok(hospitalWardDTO);
    }

    /**
     * Updates the details of a specific hospital ward.
     *
     * @param hospitalId the ID of the hospital
     * @param wardId the ID of the ward to update
     * @param createUpdateHospitalWardDTO the DTO containing updated details of the ward
     * @return the updated hospital ward details wrapped in a {@link ResponseEntity}
     */
    @PutMapping("/hospital/{hospitalId}/ward/{wardId}")
    public ResponseEntity<HospitalWardDTO> updateHospitalWard(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("wardId") Long wardId,
            @RequestBody CreateUpdateHospitalWardDTO createUpdateHospitalWardDTO
    ) {
        HospitalWardDTO updatedWardDTO = hospitalWardService.updateHospitalWard(hospitalId, wardId, createUpdateHospitalWardDTO);
        return ResponseEntity.ok(updatedWardDTO);
    }

    /**
     * Deletes a specific hospital ward by its ID within a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param wardId the ID of the ward to delete
     * @return a confirmation message of the deletion wrapped in a {@link ResponseEntity}
     */
    @DeleteMapping("/hospital/{hospitalId}/ward/{wardId}")
    public ResponseEntity<String> deleteHospitalWard(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("wardId") Long wardId
    ) {
        hospitalWardService.deleteHospitalWard(hospitalId, wardId);
        return new ResponseEntity<>("Hospital ward was deleted successfully", HttpStatus.OK);
    }
}
