package live.allone.hospital.application;

import java.util.stream.IntStream;
import live.allone.hospital.application.dto.HospitalRequest;
import live.allone.hospital.application.dto.HospitalResponse;
import live.allone.hospital.application.dto.HospitalSyncRequest;
import live.allone.hospital.application.dto.HospitalSyncResponse;
import live.allone.hospital.domain.HospitalRepository;
import live.allone.utils.PagingResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalClient hospitalClient, HospitalUpdater hospitalUpdater, HospitalRepository hospitalRepository) {
        this.hospitalClient = hospitalClient;
        this.hospitalUpdater = hospitalUpdater;
        this.hospitalRepository = hospitalRepository;
    }

    public void syncHospitals(int pageNo) {
        HospitalSyncResponse initialResponse = hospitalClient.requestHospitals(createHospitalRequest(pageNo));
        int totalCount = initialResponse.getTotalCount();
        int totalPageLimit = calculateTotalPageLimit(totalCount);

        int pageLimitToRequest = Math.min(totalPageLimit, MAX_REQUESTS + pageNo);
        IntStream.range(pageNo, pageLimitToRequest)
            .forEach(this::requestAndSaveHospitals);
    }

    private HospitalSyncResponse requestAndSaveHospitals(int pageNo) {
        HospitalSyncRequest hospitalSyncRequest = createHospitalRequest(pageNo);
        HospitalSyncResponse hospitalSyncResponse = hospitalClient.requestHospitals(hospitalSyncRequest);
        saveHospitals(hospitalSyncResponse);
        return hospitalSyncResponse;
    }

    private void saveHospitals(HospitalSyncResponse hospitalSyncResponse) {
        try {
            StopWatch stopWatch = new StopWatch(HOSPITAL_SYNC_STOPWATCH);
            stopWatch.start();
            hospitalUpdater.saveAll(hospitalSyncResponse.getItems());
            stopWatch.stop();
            LOGGER.info(stopWatch.prettyPrint());
        } catch (Exception e) {
            LOGGER.info(ERROR_WHILE_SAVING_HOSPITALS + e.getMessage());
        }
    }

    private HospitalSyncRequest createHospitalRequest(int pageNo) {
        return HospitalSyncRequest.builder()
            .numOfRows(NUM_OF_ROWS)
            .pageNo(pageNo)
            .build();
    }

    private int calculateTotalPageLimit(int totalCount) {
        return (totalCount / NUM_OF_ROWS) + 2;
    }

    @Transactional(readOnly = true)
    public PagingResponse<HospitalResponse> findHospitals(HospitalRequest hospitalRequest) {
        return null;
    }
}
