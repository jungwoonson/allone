package live.allone.hospital.application;

import live.allone.hospital.application.dto.HospitalRequest;
import live.allone.hospital.application.dto.HospitalResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

    private static final int NUM_OF_ROWS = 500;
    @Value("${api.hospital.key}")
    private static final String SERVICE_KEY = "serviceKey";

    private HospitalClient hospitalClient;
    private HospitalUpdater hospitalUpdater;

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
        hospitalUpdater.saveAll(hospitalResponse.getItems());
        return hospitalResponse;
    }

    private HospitalRequest createHospitalRequest(int pageNo) {
        return HospitalRequest.builder()
            .serviceKey(SERVICE_KEY)
            .numOfRows(NUM_OF_ROWS)
            .pageNo(pageNo)
            .build();
    }

    private int calculateTotalPageLimit(int totalCount) {
        return (totalCount / NUM_OF_ROWS) + 2;
    }
}
