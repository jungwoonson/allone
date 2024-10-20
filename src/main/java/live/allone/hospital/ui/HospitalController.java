package live.allone.hospital.ui;

import live.allone.hospital.application.HospitalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/hospital/sync")
    public String syncHospitals(@RequestParam int pageNo) {
        hospitalService.syncHospitals(pageNo);
        return "Sync hospitals";
    }
}
