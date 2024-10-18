package live.allone.hospital.ui;

import live.allone.hospital.application.HospitalService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HospitalScheduler {

    private HospitalService hospitalService;

    public HospitalScheduler(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @Scheduled(cron = "0 50 18 * * ?")
    public void syncHospitals() {
        hospitalService.syncHospitals();
    }
}
