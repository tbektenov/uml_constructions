package tbektenov.com.sau.controllers.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.CreateUpdatePrivatePharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyDTO;
import tbektenov.com.sau.dtos.pharmacy.privatePharmacy.PrivatePharmacyResponse;
import tbektenov.com.sau.services.IPrivatePharmacyService;

@RestController
@RequestMapping("/api/pp/")
public class PrivatePharmacyController {

    private IPrivatePharmacyService privatePharmacyService;

    @Autowired
    public PrivatePharmacyController(IPrivatePharmacyService privatePharmacyService) {
        this.privatePharmacyService = privatePharmacyService;
    }

    /**
     * Retrieves a paginated list of private pharmacies.
     *
     * @param pageNo the page number to retrieve, defaults to 0
     * @param pageSize the number of pharmacies per page, defaults to 10
     * @return a paginated list of private pharmacies wrapped in a PrivatePharmacyResponse
     */
    @GetMapping("privatePharmacies")
    public ResponseEntity<PrivatePharmacyResponse> getPrivatePharmacies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(privatePharmacyService.getAllPrivatePharmacies(pageNo, pageSize), HttpStatus.OK);
    }

    /**
     * Creates a new private pharmacy.
     *
     * @param createUpdatePrivatePharmacyDTO the DTO containing details of the new private pharmacy
     * @return the created private pharmacy details
     */
    @PostMapping("/create")
    public ResponseEntity<PrivatePharmacyDTO> createPrivatePharmacy(
            @RequestBody CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO
    ) {
        return new ResponseEntity<>(privatePharmacyService.createPrivatePharmacy(createUpdatePrivatePharmacyDTO), HttpStatus.CREATED);
    }

    /**
     * Retrieves the details of a specific private pharmacy by its ID.
     *
     * @param privatePharmacyId the ID of the private pharmacy
     * @return the details of the specified private pharmacy
     */
    @GetMapping("privatePharmacy/find/{id}")
    public ResponseEntity<PrivatePharmacyDTO> hospitalDetail(@PathVariable("id") Long privatePharmacyId) {
        return ResponseEntity.ok(privatePharmacyService.getPrivatePharmacyById(privatePharmacyId));
    }

    /**
     * Updates the details of a specific private pharmacy.
     *
     * @param createUpdatePrivatePharmacyDTO the DTO containing updated details of the private pharmacy
     * @param privatePharmacyId the ID of the private pharmacy to update
     * @return the updated private pharmacy details
     */
    @PutMapping("privatePharmacy/update/{id}")
    public ResponseEntity<PrivatePharmacyDTO> updateHospital(
            @RequestBody CreateUpdatePrivatePharmacyDTO createUpdatePrivatePharmacyDTO,
            @PathVariable("id") Long privatePharmacyId
    ) {
        PrivatePharmacyDTO response = privatePharmacyService.updatePrivatePharmacy(createUpdatePrivatePharmacyDTO, privatePharmacyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a specific private pharmacy by its ID.
     *
     * @param privatePharmacyId the ID of the private pharmacy to delete
     * @return a confirmation message of the deletion
     */
    @DeleteMapping("privatePharmacy/delete/{id}")
    public ResponseEntity<String> deleteHospital(@PathVariable("id") Long privatePharmacyId) {
        privatePharmacyService.deletePrivatePharmacy(privatePharmacyId);
        return new ResponseEntity<>("Hospital deleted", HttpStatus.OK);
    }

    /**
     * Adds a partner hospital to a private pharmacy.
     *
     * @param pharmacyId the ID of the private pharmacy
     * @param hospitalId the ID of the hospital to add as a partner
     * @return the updated private pharmacy details with the new partner hospital
     */
    @PostMapping("/{pharmacyId}/partners/{hospitalId}")
    public ResponseEntity<PrivatePharmacyDTO> addPartnerHospital(
            @PathVariable Long pharmacyId,
            @PathVariable Long hospitalId
    ) {
        return new ResponseEntity<>(privatePharmacyService.addPartnerHospital(pharmacyId, hospitalId), HttpStatus.OK);
    }

    /**
     * Removes a partner hospital from a private pharmacy.
     *
     * @param pharmacyId the ID of the private pharmacy
     * @param hospitalId the ID of the hospital to remove as a partner
     * @return the updated private pharmacy details without the partner hospital
     */
    @DeleteMapping("/{pharmacyId}/partners/{hospitalId}")
    public ResponseEntity<PrivatePharmacyDTO> removePartnerHospital(
            @PathVariable Long pharmacyId,
            @PathVariable Long hospitalId
    ) {
        return new ResponseEntity<>(privatePharmacyService.removePartnerHospital(pharmacyId, hospitalId), HttpStatus.OK);
    }
}
