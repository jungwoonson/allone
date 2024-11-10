package live.allone.hospital.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import live.allone.hospital.domain.OperatingHour;
import live.allone.hospital.domain.WeeklyType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@Schema(description = "병원 정보 응답")
public class HospitalResponse {

    @Schema(description = "id(primary)", example = "213")
    private Long id;
    @Schema(description = "병원ID(unique)", example = "A1124862")
    private String hospitalId;
    @Schema(description = "병원명", example = "시청하늘정신건강의학과의원")
    private String name;
    @Schema(description = "주소", example = "서울특별시 중구 무교로 13, 휘닉스빌딩 6층 (무교동)")
    private String address;
    @Schema(description = "전화번호", example = "02-756-6322")
    private String phoneNumber;
    @Schema(description = "경도", example = "126.9790216218")
    private double longitude;
    @Schema(description = "위도", example = "37.5671488075903")
    private double latitude;
    @Schema(description = "거리(m)", example = "124")
    private int distance;
    private List<WeeklySchedule> weeklySchedules;

    public static class HospitalResponseBuilder {
        public HospitalResponseBuilder weeklySchedules(OperatingHour operatingHour) {
            weeklySchedules = new ArrayList<>();
            for (WeeklyType type : WeeklyType.values()) {
                WeeklySchedule schedule = WeeklySchedule.builder()
                    .weekType(type.getValue())
                    .isOperational(operatingHour.isOperational(type))
                    .open(operatingHour.getOpen(type))
                    .close(operatingHour.getClose(type))
                    .build();
                weeklySchedules.add(schedule);
            }
            return this;
        }
    }
}
