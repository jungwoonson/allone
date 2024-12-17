package live.allone.hospital.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Schema(description = "위경도 기반 병원 목록 조회 요청")
public class HospitalRequest {

    @Schema(description = "경도", example = "126.9779692", requiredMode = REQUIRED)
    private Double longitude;
    @Schema(description = "위도", example = "37.566535", requiredMode = REQUIRED)
    private Double latitude;
    @Schema(description = "병원명", example = "아산 병원")
    private String name;
    @Schema(description = "페이지", example = "1", minimum = "1", requiredMode = REQUIRED)
    @Min(1)
    private int page;
    @Schema(description = "조회 건수", example = "10", minimum = "1", requiredMode = REQUIRED)
    @Min(1)
    private int size;
}
