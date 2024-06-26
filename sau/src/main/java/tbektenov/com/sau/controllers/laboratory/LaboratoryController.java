package tbektenov.com.sau.controllers.laboratory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.laboratory.CreateUpdateLaboratoryDTO;
import tbektenov.com.sau.dtos.laboratory.LaboratoryDTO;
import tbektenov.com.sau.services.ILaboratoryService;

import java.util.List;

@RestController
@RequestMapping("/api/lab/")
public class LaboratoryController {

    private ILaboratoryService laboratoryService;

    @Autowired
    public LaboratoryController(ILaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    /**
     * Creates a new laboratory for the specified hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param createUpdateLaboratoryDTO the DTO containing details of the new laboratory
     * @return the created laboratory details
     */
    @PostMapping("/create/{hospitalId}")
    public ResponseEntity<LaboratoryDTO> createLaboratory(
            @PathVariable("hospitalId") Long hospitalId,
            @RequestBody CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO
    ) {
        return new ResponseEntity<>(laboratoryService.createLaboratory(hospitalId, createUpdateLaboratoryDTO), HttpStatus.CREATED);
    }

    /**
     * Retrieves all laboratories for a specific hospital.
     *
     * @param hospitalId the ID of the hospital
     * @return a list of laboratories associated with the specified hospital
     */
    @GetMapping("/hospital/{hospitalId}/laboratories")
    public ResponseEntity<List<LaboratoryDTO>> getLaboratoriesByHospitalId(@PathVariable("hospitalId") Long hospitalId) {
        return ResponseEntity.ok(laboratoryService.getLaboratoriesByHospitalId(hospitalId));
    }

    /**
     * Retrieves the details of a specific laboratory by its ID within a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param laboratoryId the ID of the laboratory
     * @return the details of the specified laboratory
     */
    @GetMapping("/hospital/{hospitalId}/laboratory/{laboratoryId}")
    public ResponseEntity<LaboratoryDTO> getLaboratoryById(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("laboratoryId") Long laboratoryId
    ) {
        LaboratoryDTO laboratoryDTO = laboratoryService.getLaboratoryById(hospitalId, laboratoryId);
        return ResponseEntity.ok(laboratoryDTO);
    }

    /**
     * Updates the details of a specific laboratory.
     *
     * @param hospitalId the ID of the hospital
     * @param laboratoryId the ID of the laboratory to update
     * @param createUpdateLaboratoryDTO the DTO containing updated details of the laboratory
     * @return the updated laboratory details
     */
    @PutMapping("/hospital/{hospitalId}/laboratory/{laboratoryId}")
    public ResponseEntity<LaboratoryDTO> updateLaboratory(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("laboratoryId") Long laboratoryId,
            @RequestBody CreateUpdateLaboratoryDTO createUpdateLaboratoryDTO
    ) {
        LaboratoryDTO laboratoryDTO = laboratoryService.updateLaboratory(hospitalId, laboratoryId, createUpdateLaboratoryDTO);
        return ResponseEntity.ok(laboratoryDTO);
    }

    /**
     * Deletes a specific laboratory by its ID within a hospital.
     *
     * @param hospitalId the ID of the hospital
     * @param laboratoryId the ID of the laboratory to delete
     * @return a confirmation message of the deletion
     */
    @DeleteMapping("/hospital/{hospitalId}/laboratory/{laboratoryId}")
    public ResponseEntity<String> deleteLaboratory(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("laboratoryId") Long laboratoryId
    ) {
        laboratoryService.deleteLaboratory(hospitalId, laboratoryId);
        return new ResponseEntity<>("Laboratory was deleted successfully", HttpStatus.OK);
    }
}
