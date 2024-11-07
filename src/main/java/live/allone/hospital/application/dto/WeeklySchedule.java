package live.allone.hospital.application.dto;

import java.time.LocalTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class WeeklySchedule {

    private final int weekType;
    private final boolean isOperational;
    private LocalTime open;
    private LocalTime close;
}
