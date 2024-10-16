package live.allone.hospital.application;

import live.allone.hospital.application.dto.HospitalRequest;
import live.allone.hospital.application.dto.HospitalResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpHeaders.ACCEPT;

@Service
public class HospitalClient {

    private static final String NUM_OF_ROWS = "numOfRows";
    private static final String PAGE_NO = "pageNo";
    private static final String SERVICE_KEY = "serviceKey";

    @Value("${api.hospital.fulldown.url}")
    private String fullDownUrl;

    public HospitalResponse requestHospitals(HospitalRequest hospitalRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT, MediaType.APPLICATION_XML_VALUE);

        HttpEntity<HospitalRequest> httpEntity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.fromHttpUrl(fullDownUrl)
                .queryParam(NUM_OF_ROWS, hospitalRequest.getNumOfRows())
                .queryParam(PAGE_NO, hospitalRequest.getPageNo())
                .queryParam(SERVICE_KEY, hospitalRequest.getServiceKey())
                .toUriString();

        return new RestTemplate()
                .exchange(url, HttpMethod.GET, httpEntity, HospitalResponse.class)
                .getBody();
    }
}
