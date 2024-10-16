package live.allone.hospital.unit;

import java.util.List;
import live.allone.hospital.application.HospitalClient;
import live.allone.hospital.application.dto.HospitalItem;
import live.allone.hospital.application.dto.HospitalRequest;
import live.allone.hospital.application.dto.HospitalResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HospitalClient 관련 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class HospitalClientTest {

    @Autowired
    private HospitalClient hospitalClient;

    @DisplayName("병원 스크랩 함수는, 공공데이터포털 API를 사용하여 병원 정보를 반환한다.")
    @Test
    void requestHospitalsTest() {
        // given
        HospitalRequest hospitalRequest = HospitalRequest.builder()
            .serviceKey("test-service-key")
            .pageNo(1)
            .numOfRows(10)
            .build();

        // when
        HospitalResponse hospitalResponse = hospitalClient.requestHospitals(hospitalRequest);

        // then
        List<HospitalItem> items = hospitalResponse.getItems();
        assertThat(items.size()).isEqualTo(10);
    }
}
