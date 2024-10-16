package live.allone.hospital.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class OperatingHour {

    @Column
    private String monOpen;
    @Column
    private String monClose;
    @Column
    private String tueOpen;
    @Column
    private String tueClose;
    @Column
    private String wedOpen;
    @Column
    private String wedClose;
    @Column
    private String thuOpen;
    @Column
    private String thuClose;
    @Column
    private String friOpen;
    @Column
    private String friClose;
    @Column
    private String satOpen;
    @Column
    private String satClose;
    @Column
    private String sunOpen;
    @Column
    private String sunClose;
    @Column
    private String holidayOpen;
    @Column
    private String holidayClose;

    public OperatingHour() {
    }
}
