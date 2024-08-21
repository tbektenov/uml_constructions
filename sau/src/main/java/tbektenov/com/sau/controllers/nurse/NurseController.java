package tbektenov.com.sau.controllers.nurse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tbektenov.com.sau.dtos.nurse.CreateNurseDTO;
import tbektenov.com.sau.dtos.nurse.NurseDTO;
import tbektenov.com.sau.services.INurseService;

import java.util.List;

@Controller
@RequestMapping("/api/nurse")
public class NurseController {

    private INurseService nurseService;

    @Autowired
    public NurseController(INurseService nurseService) {
        this.nurseService = nurseService;
    }

    /**
     * Retrieves a list of all nurses.
     *
     * @return a list of NurseDTO representing all nurses
     */
    @GetMapping("nurses")
    public ResponseEntity<List<NurseDTO>> getNurses() {
        return new ResponseEntity<>(nurseService.getAllNurses(), HttpStatus.OK);
    }

    /**
     * Assigns a nurse role to a user and creates a new nurse record.
     *
     * @param createNurseDTO the data transfer object containing nurse details
     * @return the created nurse details wrapped in a NurseDTO
     */
    @PostMapping("/giveNurseRole")
    public ResponseEntity<NurseDTO> createNurse(
            @RequestBody CreateNurseDTO createNurseDTO
            ) {
        return new ResponseEntity<>(nurseService.createNurse(createNurseDTO), HttpStatus.CREATED);
    }
}
