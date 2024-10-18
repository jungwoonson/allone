package live.allone.hospital.application;

import live.allone.hospital.application.dto.HospitalRequest;
import live.allone.hospital.application.dto.HospitalResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class HospitalService {

    private static final Logger LOGGER = LogManager.getLogger(HospitalService.class);
    public static final String ERROR_WHILE_SAVING_HOSPITALS = "Error while saving hospitals: ";
    public static final String HOSPITAL_SYNC_STOPWATCH = "Hospital Sync Stopwatch";

    @Value("${hospital.request.num-of-rows}")
    private int NUM_OF_ROWS;

    private final HospitalClient hospitalClient;
    private final HospitalUpdater hospitalUpdater;

    public HospitalService(HospitalClient hospitalClient, HospitalUpdater hospitalUpdater) {
        this.hospitalClient = hospitalClient;
        this.hospitalUpdater = hospitalUpdater;
    }

    public void syncHospitals() {
        HospitalResponse initialResponse = requestAndSaveHospitals(1);
        int totalCount = initialResponse.getTotalCount();
        int totalPageLimit = calculateTotalPageLimit(totalCount);

        for (int i = 2; i < totalPageLimit; i++) {
            requestAndSaveHospitals(i);
        }

        hospitalUpdater.deleteNotUpdatedHospitals();
    }

    private HospitalResponse requestAndSaveHospitals(int pageNo) {
        HospitalRequest hospitalRequest = createHospitalRequest(pageNo);
        HospitalResponse hospitalResponse = hospitalClient.requestHospitals(hospitalRequest);
        saveHospitals(hospitalResponse);
        return hospitalResponse;
    }

    private void saveHospitals(HospitalResponse hospitalResponse) {
        try {
            StopWatch stopWatch = new StopWatch(HOSPITAL_SYNC_STOPWATCH);
            stopWatch.start();
            hospitalUpdater.saveAll(hospitalResponse.getItems());
            stopWatch.stop();
            LOGGER.info(stopWatch.prettyPrint());
        } catch (Exception e) {
            LOGGER.info(ERROR_WHILE_SAVING_HOSPITALS + e.getMessage());
        }
    }

    private HospitalRequest createHospitalRequest(int pageNo) {
        return HospitalRequest.builder()
            .numOfRows(NUM_OF_ROWS)
            .pageNo(pageNo)
            .build();
    }

    private int calculateTotalPageLimit(int totalCount) {
        return (totalCount / NUM_OF_ROWS) + 2;
    }
}
