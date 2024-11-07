package live.allone.hospital.ui;

import live.allone.hospital.application.HospitalService;
import live.allone.hospital.application.dto.HospitalRequest;
import live.allone.hospital.application.dto.HospitalResponse;
import live.allone.utils.PagingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/hospital")
    public ResponseEntity<PagingResponse<HospitalResponse>> getHospitals(@ModelAttribute HospitalRequest hospitalRequest) {
        return ResponseEntity.ok(hospitalService.findHospitals(hospitalRequest));
    }

    @PatchMapping("/hospital/sync")
    public String syncHospitals(@RequestParam int pageNo) {
        hospitalService.syncHospitals(pageNo);
        return "Sync hospitals";
    }
}
