package live.allone.hospital.mock;

import live.allone.hospital.application.dto.HospitalRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class HospitalApiMockController {

    @GetMapping(value = "/B552657/HsptlAsembySearchService/getHsptlMdcncFullDown", produces = "application/xml")
    public ResponseEntity<byte[]> getHsptlMdcncFullDown(@ModelAttribute HospitalRequest request) throws IOException {
        String serviceKey = request.getServiceKey();
        if (!StringUtils.hasText(serviceKey)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Resource resource = new ClassPathResource("hospitals-updated.xml");
        Path filePath = resource.getFile().toPath();

        byte[] xmlBytes = Files.readAllBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        return new ResponseEntity<>(xmlBytes, headers, HttpStatus.OK);
    }
}
