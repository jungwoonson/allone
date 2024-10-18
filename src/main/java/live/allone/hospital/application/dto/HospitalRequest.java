package live.allone.hospital.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalRequest {

    private int numOfRows;
    private int pageNo;
}
