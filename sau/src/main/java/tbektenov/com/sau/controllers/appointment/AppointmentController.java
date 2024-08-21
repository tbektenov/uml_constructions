package tbektenov.com.sau.controllers.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.config.CustomUserDetailsService;
import tbektenov.com.sau.dtos.appointment.AppointmentDTO;
import tbektenov.com.sau.dtos.appointment.CreateAppointmentDTO;
import tbektenov.com.sau.repositories.DoctorRepo;
import tbektenov.com.sau.repositories.PatientRepo;
import tbektenov.com.sau.services.IAppointmentService;

import java.util.List;

@Controller
@RequestMapping("/api/appoint/")
public class AppointmentController {
    private IAppointmentService appointmentService;
    private CustomUserDetailsService userDetailsService;
    @Autowired
    public AppointmentController(IAppointmentService appointmentService, PatientRepo patientRepo, DoctorRepo doctorRepo, CustomUserDetailsService userDetailsService) {
        this.appointmentService = appointmentService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Creates a new appointment between a doctor and a patient on the specified date.
     * @return the created appointment details
     */
    @PostMapping("create/")
    public ResponseEntity<AppointmentDTO> createAppointment(
            @RequestBody CreateAppointmentDTO createAppointmentDTO
    ) {
       return ResponseEntity.ok(appointmentService.createAppointment(createAppointmentDTO));
    }

    /**
     * Cancels the appointment with the given ID.
     *
     * @param appointId the ID of the appointment to cancel
     * @return a confirmation message
     */
    @DeleteMapping("delete/{appointId}")
    public ResponseEntity<String> createAppointment(
            @PathVariable Long appointId
    ) {
        appointmentService.cancelAppointmentById(appointId);
        return ResponseEntity.ok("Appointment was canceled.");
    }

    @GetMapping("patient/getUpcoming/")
    public ResponseEntity<List<AppointmentDTO>> getUpcomingUserAppointments(
    ) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByPatientId(
            userDetailsService.getLoggedUser().getId()
        ));
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
