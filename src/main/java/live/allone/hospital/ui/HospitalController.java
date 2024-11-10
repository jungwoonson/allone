package live.allone.hospital.ui;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "병원 목록 조회", description = "경도, 위도 기준으로 병원 목록을 조회합니다.")
    @GetMapping("/hospital")
    public ResponseEntity<PagingResponse<HospitalResponse>> getHospitals(@ModelAttribute HospitalRequest hospitalRequest) {
        return ResponseEntity.ok(hospitalService.findHospitals(hospitalRequest));
    }
}
