package live.allone.hospital.application;

import static org.springframework.http.HttpHeaders.ACCEPT;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import live.allone.hospital.application.dto.HospitalSyncRequest;
import live.allone.hospital.application.dto.HospitalSyncResponse;
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

    public HospitalSyncResponse requestHospitals(HospitalSyncRequest hospitalSyncRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT, MediaType.APPLICATION_XML_VALUE);

        HttpEntity<HospitalSyncRequest> httpEntity = new HttpEntity<>(headers);

        String serviceKeyEncoded = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);

        URI uri = UriComponentsBuilder.fromHttpUrl(fullDownUrl)
            .queryParam(NUM_OF_ROWS, hospitalSyncRequest.getNumOfRows())
            .queryParam(PAGE_NO, hospitalSyncRequest.getPageNo())
            .queryParam(SERVICE_KEY, serviceKeyEncoded)
            .build(true)
            .toUri();

        ResponseEntity<HospitalSyncResponse> response = requestHospitals(uri, httpEntity,
            hospitalSyncRequest);

        return response.getBody();
    }

    private ResponseEntity<HospitalSyncResponse> requestHospitals(URI uri,
        HttpEntity<HospitalSyncRequest> httpEntity, HospitalSyncRequest hospitalSyncRequest) {
        RestTemplate restTemplate = new RestTemplate();

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            ResponseEntity<HospitalSyncResponse> response = sendRequest(restTemplate, uri, httpEntity);
            if (isSuccessfulResponse(response)) {
                return response;
            }
            threadSleep();
        }

        throw new RequestHospitalException(hospitalSyncRequest);
    }

    private ResponseEntity<HospitalSyncResponse> sendRequest(RestTemplate restTemplate, URI uri, HttpEntity<HospitalSyncRequest> httpEntity) {
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, HospitalSyncResponse.class);
        } catch (RestClientException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    private boolean isSuccessfulResponse(ResponseEntity<HospitalSyncResponse> response) {
        HospitalSyncResponse hospitalSyncResponse = response.getBody();
        return hospitalSyncResponse != null && hospitalSyncResponse.getBody() != null;
    }

    @SneakyThrows
    private void threadSleep() {
        Thread.sleep(500);
    }
}
