package live.allone.hospital.application;

import java.util.stream.IntStream;
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
    private static final String ERROR_WHILE_SAVING_HOSPITALS = "Error while saving hospitals: ";
    private static final String HOSPITAL_SYNC_STOPWATCH = "Hospital Sync Stopwatch";
    private static final int PAGE_OFFSET = 1;

    @Value("${hospital.request.max}")
    private int MAX_REQUESTS;

    @Value("${hospital.request.num-of-rows}")
    private int NUM_OF_ROWS;

    private final HospitalClient hospitalClient;
    private final HospitalUpdater hospitalUpdater;

    public HospitalService(HospitalClient hospitalClient, HospitalUpdater hospitalUpdater) {
        this.hospitalClient = hospitalClient;
        this.hospitalUpdater = hospitalUpdater;
    }

    public void syncHospitals(int pageNo) {
        HospitalResponse initialResponse = hospitalClient.requestHospitals(createHospitalRequest(pageNo));
        int totalCount = initialResponse.getTotalCount();
        int totalPageLimit = calculateTotalPageLimit(totalCount);

        int pageLimitToRequest = Math.min(totalPageLimit, MAX_REQUESTS + pageNo);
        IntStream.range(pageNo, pageLimitToRequest)
            .forEach(this::requestAndSaveHospitals);
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
