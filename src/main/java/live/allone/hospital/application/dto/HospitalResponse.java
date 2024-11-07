package live.allone.hospital.application.dto;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class HospitalResponse {

    private Long id;
    private String hospitalId;
    private int sequenceNumber;
    private String name;
    private String address;
    private String phoneNumber;
    private double longitude;
    private double latitude;
    private int distance;
    private List<WeeklySchedule> weeklySchedules;
}
