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

@Controller
@RequestMapping("/appointments/")
public class AppointmentController {
    private final DoctorServiceImpl doctorServiceImpl;
    private IAppointmentService appointmentService;
    private CustomUserDetailsService customUserDetailsService;
    private PatientRepo patientRepo;
    private DoctorRepo doctorRepo;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService,
                                 PatientRepo patientRepo,
                                 DoctorRepo doctorRepo,
                                 CustomUserDetailsService customUserDetailsService, DoctorServiceImpl doctorServiceImpl) {
        this.appointmentService = appointmentService;
        this.customUserDetailsService = customUserDetailsService;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    /**
     * Creates a new appointment between a doctor and a patient on the specified date.
     *
     * @return the created appointment details
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
     * Cancels the appointment with the given ID.
     *
     * @param appointId the ID of the appointment to cancel
     * @return a confirmation message
     */
    @DeleteMapping("delete/{appointId}")
    public ResponseEntity<String> cancelAppointment(
            @PathVariable Long appointId
    ) {
        appointmentService.cancelAppointmentById(appointId);
        return ResponseEntity.ok("Appointment was canceled.");
    }

    @PutMapping("/finish/{appointId}")
    public ResponseEntity<String> finishAppointment(
            @PathVariable Long appointId
    ) {
        appointmentService.archiveAppointmentById(appointId);
        return ResponseEntity.ok("Appointment was finished.");
    }

    @GetMapping("patient/getUpcoming/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getUpcomingUserAppointmentsByPatientId(
            @PathVariable Long patientId
    ) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByPatientId(
                patientId
        ));
    }


}
