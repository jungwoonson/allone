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

    @Scheduled(cron = "0 0 2 21 * ?")
    public void syncHospitals() {
        hospitalService.syncHospitals(1);
    }

    @Scheduled(cron = "0 30 2 21 * ?")
    public void syncHospitals1() {
        hospitalService.syncHospitals(101);
    }

    @Scheduled(cron = "0 0 3 21 * ?")
    public void syncHospitals2() {
        hospitalService.syncHospitals(201);
    }

    @Scheduled(cron = "0 30 3 21 * ?")
    public void syncHospitals3() {
        hospitalService.syncHospitals(301);
    }

    @Scheduled(cron = "0 0 4 21 * ?")
    public void syncHospitals4() {
        hospitalService.syncHospitals(401);
    }

    @Scheduled(cron = "0 30 4 21 * ?")
    public void syncHospitals5() {
        hospitalService.syncHospitals(501);
    }

    @Scheduled(cron = "0 0 5 21 * ?")
    public void syncHospitals6() {
        hospitalService.syncHospitals(601);
    }

    @Scheduled(cron = "0 30 5 21 * ?")
    public void syncHospitals7() {
        hospitalService.syncHospitals(701);
    }
}
