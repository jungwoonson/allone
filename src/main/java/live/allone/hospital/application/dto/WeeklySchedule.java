package live.allone.hospital.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "요일별 운영여부,운영시간 응답 (요일타입: 월~일=1~7, 휴일=8)")
public class WeeklySchedule {

    @Schema(description = "요일타입(월~일=1~7, 휴일=8)", example = "1")
    private final int weekType;
    @Schema(description = "운영여부(true/false)", example = "true")
    private final boolean isOperational;
    @Schema(description = "여는시간", example = "10:00")
    private String open;
    @Schema(description = "닫는시간", example = "17:30")
    private String close;
}
