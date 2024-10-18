package live.allone.hospital.application;

import static org.springframework.http.HttpHeaders.ACCEPT;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import live.allone.hospital.application.dto.HospitalRequest;
import live.allone.hospital.application.dto.HospitalResponse;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HospitalClient {

    private static final Logger LOGGER = LogManager.getLogger(HospitalClient.class);

    private static final String NUM_OF_ROWS = "numOfRows";
    private static final String PAGE_NO = "pageNo";
    private static final String SERVICE_KEY = "serviceKey";
    public static final int MAX_RETRIES = 5;

    @Value("${api.hospital.fulldown.url}")
    private String fullDownUrl;
    @Value("${api.hospital.key}")
    private String serviceKey;

    public HospitalResponse requestHospitals(HospitalRequest hospitalRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT, MediaType.APPLICATION_XML_VALUE);

        HttpEntity<HospitalRequest> httpEntity = new HttpEntity<>(headers);

        String serviceKeyEncoded = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);

        URI uri = UriComponentsBuilder.fromHttpUrl(fullDownUrl)
            .queryParam(NUM_OF_ROWS, hospitalRequest.getNumOfRows())
            .queryParam(PAGE_NO, hospitalRequest.getPageNo())
            .queryParam(SERVICE_KEY, serviceKeyEncoded)
            .build(true)
            .toUri();

        ResponseEntity<HospitalResponse> response = requestHospitals(uri, httpEntity,
            hospitalRequest);

        return response.getBody();
    }

    private ResponseEntity<HospitalResponse> requestHospitals(URI uri,
        HttpEntity<HospitalRequest> httpEntity, HospitalRequest hospitalRequest) {
        RestTemplate restTemplate = new RestTemplate();

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            ResponseEntity<HospitalResponse> response = sendRequest(restTemplate, uri, httpEntity);
            if (isSuccessfulResponse(response)) {
                return response;
            }
            threadSleep();
        }

        throw new RequestHospitalException(hospitalRequest);
    }

    private ResponseEntity<HospitalResponse> sendRequest(RestTemplate restTemplate, URI uri, HttpEntity<HospitalRequest> httpEntity) {
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, HospitalResponse.class);
        } catch (RestClientException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    private boolean isSuccessfulResponse(ResponseEntity<HospitalResponse> response) {
        HospitalResponse hospitalResponse = response.getBody();
        return hospitalResponse != null && hospitalResponse.getBody() != null;
    }

    @SneakyThrows
    private void threadSleep() {
        Thread.sleep(500);
    }
}
