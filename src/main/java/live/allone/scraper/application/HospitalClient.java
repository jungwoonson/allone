package live.allone.scraper.application;

import live.allone.scraper.application.dto.HospitalRequest;
import live.allone.scraper.application.dto.HospitalResponse;
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

    @Value("${api.hospital.fulldown.url}")
    private String fullDownUrl;
    @Value("${api.hospital.key}")
    private String serviceKey;

    public HospitalResponse requestHospitals(HospitalRequest hospitalRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT, MediaType.APPLICATION_XML_VALUE);

        HttpEntity<HospitalRequest> httpEntity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.fromHttpUrl(fullDownUrl)
                .queryParam("numOfRows", hospitalRequest.getNumOfRows())
                .queryParam("pageNo", hospitalRequest.getPageNo())
                .queryParam("serviceKey", serviceKey)
                .toUriString();

        return new RestTemplate()
                .exchange(url, HttpMethod.GET, httpEntity, HospitalResponse.class)
                .getBody();
    }
}
