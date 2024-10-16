package live.allone.hospital.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import live.allone.hospital.application.HospitalUpdater;
import live.allone.hospital.application.dto.HospitalResponse;
import live.allone.hospital.domain.Hospital;
import live.allone.hospital.domain.HospitalRepository;
import live.allone.hospital.ui.HospitalScheduler;
import live.allone.utils.DatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("병원 스케줄러 인수 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class HospitalSchedulerAcceptanceTest {

    public static final int EXISTING_HOSPITAL_COUNT = 10;
    private static final String UPDATED_HOSPITAL_NAME = "박영수치과의원수정된이름";
    private static final String CREATED_HOSPITAL_ID = "A1115766";
    private static final String DELETED_HOSPITAL_ID = "A1119337";

    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private HospitalScheduler hospitalScheduler;
    @Autowired
    private HospitalUpdater hospitalUpdater;
    @Autowired
    private HospitalRepository hospitalRepository;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    /**
     * Given 병원이 존재하고, 모든 병원의 동기화 날짜를 어제로 설정하고,
     * When 병원을 동기화 하면,
     * Then 새로운 병원은 추가하고, 이미 존재하는 병원은 업데이트하고, 동기화되지 않은 병원은 제거된다
     */
    @DisplayName("병원 동기화 함수는, 공공데이터포털 API를 사용하여 병원 정보를 동기화한다.")
    @Test
    void syncHospitalsTest() throws IOException {
        // given
        HospitalResponse hospitalResponse = loadHospitalResponse();
        hospitalUpdater.saveAll(hospitalResponse.getItems());

        LocalDateTime synchronizedAt = LocalDateTime.now().minusDays(1);
        hospitalRepository.updateSynchronizedAt(synchronizedAt);

        // when
        hospitalScheduler.syncHospitals();

        // then
        validateHospitals();
    }

    private HospitalResponse loadHospitalResponse() throws IOException {
        ClassPathResource resource = new ClassPathResource("hospitals-prepared.xml");
        File xmlFile = resource.getFile();
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xmlFile, HospitalResponse.class);
    }

    private void validateHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<String> hospitalNames = hospitals.stream()
            .map(Hospital::getName)
            .toList();
        List<String> hospitalIds = hospitals.stream()
            .map(Hospital::getHospitalId)
            .toList();

        assertThat(hospitals.size()).isEqualTo(EXISTING_HOSPITAL_COUNT);
        assertThat(hospitalNames).contains(UPDATED_HOSPITAL_NAME);
        assertThat(hospitalIds).contains(CREATED_HOSPITAL_ID);
        assertThat(hospitalIds).doesNotContain(DELETED_HOSPITAL_ID);
    }
}
