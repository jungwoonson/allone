package live.allone.scraper.unit;

import live.allone.scraper.application.HospitalClient;
import live.allone.scraper.application.dto.HospitalRequest;
import live.allone.scraper.application.dto.HospitalResponse;
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
        HospitalRequest hospitalRequest = new HospitalRequest();

        // when
        HospitalResponse hospitalResponse = hospitalClient.requestHospitals(hospitalRequest);

        // then
        assertThat(hospitalResponse.getTotalCount()).isEqualTo(4);
    }
}
