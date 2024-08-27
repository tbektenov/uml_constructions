package tbektenov.com.sau.controllers.appointment;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.config.CustomUserDetailsService;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;
import tbektenov.com.sau.dtos.doctor.DoctorDTO;
import tbektenov.com.sau.models.user.UserEntity;
import tbektenov.com.sau.models.user.userRoles.Specialization;
import tbektenov.com.sau.repositories.DoctorRepo;
import tbektenov.com.sau.repositories.PatientRepo;
import tbektenov.com.sau.services.IAppointmentService;
import tbektenov.com.sau.services.implementation.DoctorServiceImpl;

import java.util.List;

/**
 * Controller class that handles requests related to appointments.
 */
@Controller
@RequestMapping("/appointments/")
public class AppointmentController {

    private final DoctorServiceImpl doctorServiceImpl;
    private final IAppointmentService appointmentService;
    private final CustomUserDetailsService customUserDetailsService;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;

    /**
     * Constructs an {@code AppointmentController} with the required dependencies.
     *
     * @param appointmentService the service handling appointment logic
     * @param patientRepo the repository for managing patient data
     * @param doctorRepo the repository for managing doctor data
     * @param customUserDetailsService the service for managing user details
     * @param doctorServiceImpl the service for managing doctor-related operations
     */
    @Autowired
    public AppointmentController(IAppointmentService appointmentService,
                                 PatientRepo patientRepo,
                                 DoctorRepo doctorRepo,
                                 CustomUserDetailsService customUserDetailsService,
                                 DoctorServiceImpl doctorServiceImpl) {
        this.appointmentService = appointmentService;
        this.customUserDetailsService = customUserDetailsService;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    /**
     * Handles the creation of a new appointment between a doctor and a patient.
     *
     * @param createAppointmentDTO the data transfer object containing appointment details
     * @param result the binding result for handling validation errors
     * @param model the model to add attributes used in the view
     * @return a string indicating the next view to render
     */
    @PostMapping("create")
    public String createAppointment(
            @Valid @ModelAttribute CreateAppointmentDTO createAppointmentDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "newAppointment";
        }
        appointmentService.createAppointment(createAppointmentDTO);
        return "redirect:/home";
    }

    /**
     * Renders the form for creating a new appointment.
     *
     * @param model the model to add attributes used in the view
     * @param session the current HTTP session
     * @return the view name for creating a new appointment
     */
    @GetMapping("new")
    public String newAppointment(
            Model model,
            HttpSession session
    ) {
        UserEntity user;
        if (session.getAttribute("user") == null) {
            user = customUserDetailsService.getLoggedUser();
            session.setAttribute("user", user);
        } else {
            user = (UserEntity) session.getAttribute("user");
        }
        List<DoctorDTO> doctors = doctorServiceImpl.getAllDoctors();

        List<String> hospitals = doctors.stream()
                .map(DoctorDTO::getHospitalName)
                .distinct()
                .toList();

        List<Specialization> specializations = doctors.stream()
                .map(DoctorDTO::getSpecialization)
                .distinct()
                .toList();

        model.addAttribute("user", user);
        model.addAttribute("doctors", doctors);
        model.addAttribute("hospitals", hospitals);
        model.addAttribute("specializations", specializations);
        return "newAppointment";
    }

    /**
     * Cancels the appointment with the specified ID.
     *
     * @param appointId the ID of the appointment to cancel
     * @return a {@link ResponseEntity} with a confirmation message
     */
    @DeleteMapping("delete/{appointId}")
    public ResponseEntity<String> cancelAppointment(
            @PathVariable Long appointId
    ) {
        appointmentService.cancelAppointmentById(appointId);
        return ResponseEntity.ok("Appointment was canceled.");
    }

    /**
     * Marks the appointment with the specified ID as finished.
     *
     * @param appointId the ID of the appointment to finish
     * @return a {@link ResponseEntity} with a confirmation message
     */
    @PutMapping("/finish/{appointId}")
    public ResponseEntity<String> finishAppointment(
            @PathVariable Long appointId
    ) {
        appointmentService.archiveAppointmentById(appointId);
        return ResponseEntity.ok("Appointment was finished.");
    }

    /**
     * Retrieves upcoming appointments for the patient with the specified ID.
     *
     * @param patientId the ID of the patient whose appointments are to be retrieved
     * @return a {@link ResponseEntity} containing a list of upcoming appointments
     */
    @GetMapping("patient/getUpcoming/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getUpcomingUserAppointmentsByPatientId(
            @PathVariable Long patientId
    ) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByPatientId(patientId));
    }
}
