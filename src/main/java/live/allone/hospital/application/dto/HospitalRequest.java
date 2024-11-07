package live.allone.hospital.application.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class HospitalRequest {

    private Double longitude;
    private Double latitude;
    @Min(1)
    private int page;
    @Min(1)
    private int size;
}
