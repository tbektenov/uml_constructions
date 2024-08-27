package tbektenov.com.sau.controllers.hospital;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.hospital.CreateUpdateHospitalDTO;
import tbektenov.com.sau.dtos.hospital.HospitalAndDoctorsDTO;
import tbektenov.com.sau.dtos.hospital.HospitalDTO;
import tbektenov.com.sau.dtos.hospital.HospitalResponse;
import tbektenov.com.sau.models.hospital.Hospital;
import tbektenov.com.sau.services.IHospitalService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller class that handles requests related to hospitals.
 */
@Controller
public class HospitalController {

    private final IHospitalService hospitalService;

    /**
     * Constructs a {@code HospitalController} with the specified {@code IHospitalService}.
     *
     * @param hospitalService the service handling hospital-related operations
     */
    @Autowired
    public HospitalController(IHospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    /**
     * Retrieves a paginated list of hospitals and displays them.
     *
     * @param pageNo the page number to retrieve, defaults to 0
     * @param pageSize the number of hospitals per page, defaults to 5
     * @param model the model to add attributes to
     * @param session the HTTP session to store the hospital content
     * @return the name of the view to render
     */
    @GetMapping("/hospitals")
    public String showHospitals(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            Model model,
            HttpSession session
    ) {
        HospitalResponse hospitalResponse = hospitalService.getAllHospitals(pageNo, pageSize);

        model.addAttribute("hospitals", hospitalResponse);
        session.setAttribute("content", hospitalResponse.getContent());
        return ("hospitals");
    }

    /**
     * Retrieves the doctors associated with a specific hospital and displays them.
     *
     * @param hospitalId the ID of the hospital to retrieve doctors for
     * @param model the model to add attributes to
     * @param session the HTTP session to retrieve the hospital content from
     * @return the name of the view to render
     */
    @GetMapping("/hospitals/{hospitalId}")
    public String getDoctorsFromHospital(
            @PathVariable Long hospitalId,
            Model model,
            HttpSession session
    ) {
        List<HospitalDTO> hospitalDTOS = (List<HospitalDTO>) session.getAttribute("content");

        if (hospitalDTOS != null) {
            Optional<HospitalDTO> matchingHospital = hospitalDTOS.stream()
                    .filter(hospital -> hospital.getHospitalId().equals(hospitalId))
                    .findFirst();

            matchingHospital.ifPresent(hospitalDTO -> model.addAttribute("doctors", hospitalDTO.getDoctors()));
        }
        return "hospital-details";
    }

    /**
     * Creates a new hospital with the provided details.
     *
     * @param createUpdateHospitalDTO the data transfer object containing hospital details
     * @return the created hospital details wrapped in a {@link ResponseEntity}
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
     * @return the details of the specified hospital wrapped in a {@link ResponseEntity}
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
     * @return the updated hospital details wrapped in a {@link ResponseEntity}
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
     * @return a confirmation message wrapped in a {@link ResponseEntity}
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
     * @return the updated hospital details with the new partner pharmacy wrapped in a {@link ResponseEntity}
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
     * @return the updated hospital details without the partner pharmacy wrapped in a {@link ResponseEntity}
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
     * @return the updated hospital details with the new doctor wrapped in a {@link ResponseEntity}
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
     * @return the updated hospital details without the doctor wrapped in a {@link ResponseEntity}
     */
    @DeleteMapping("{hospitalId}/doctors/{doctorId}")
    public ResponseEntity<HospitalDTO> fireDoctor(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("doctorId") Long doctorId
    ) {
        return new ResponseEntity<>(hospitalService.fireDoctor(hospitalId, doctorId), HttpStatus.OK);
    }
}
