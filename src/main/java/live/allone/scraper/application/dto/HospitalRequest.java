package live.allone.scraper.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalRequest {

    private int numOfRows = 10;
    private int pageNo = 1;
    private String serviceKey;
}
