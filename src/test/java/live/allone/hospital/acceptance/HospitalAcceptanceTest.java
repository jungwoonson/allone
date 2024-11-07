package live.allone.hospital.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import live.allone.hospital.application.HospitalService;
import live.allone.utils.DatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("병원 조회 인수테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class HospitalAcceptanceTest {

    private static final double LON = 126.86380518096868;
    private static final double LAT = 37.526705407963654;
    private static final int PAGE = 1;
    private static final int SIZE = 3;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private HospitalService hospitalService;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
        hospitalService.syncHospitals(PAGE);
    }

    /**
     * Given 병원이 등록되어있고,
     * When 병원 목록를 요청하면,
     * Then 병원 목록을 응답한다.
     */
    @DisplayName("병원 목록 요청은, 주어진 경도 위도 기반으로 가장 가까운 병원 목록을 응답한다.")
    @Test
    void createLineTest() {
        // when
        ExtractableResponse<Response> response = requestGetHospital();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getIds(response).size()).isEqualTo(SIZE);
    }

    private static List<Long> getIds(ExtractableResponse<Response> response) {
        return response.jsonPath()
            .getList("data.id", Long.class);
    }

    private static ExtractableResponse<Response> requestGetHospital() {
        String url = "/hospital?longitude=%s&latitude=%s&page=%s&size=%s";

        return RestAssured.given().log().all()
            .when().get(String.format(url, LON, LAT, PAGE, SIZE))
            .then().log().all()
            .extract();
    }
}
