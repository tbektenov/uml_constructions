package tbektenov.com.sau.controllers.hospital;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tbektenov.com.sau.dtos.hospital.HospitalDTO;
import tbektenov.com.sau.dtos.hospital.HospitalResponse;
import tbektenov.com.sau.services.IHospitalService;

import java.util.List;
import java.util.Optional;

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
}
