package live.allone.hospital.domain;

import static live.allone.hospital.domain.WeeklyType.FRIDAY;
import static live.allone.hospital.domain.WeeklyType.HOLIDAY;
import static live.allone.hospital.domain.WeeklyType.MONDAY;
import static live.allone.hospital.domain.WeeklyType.SATURDAY;
import static live.allone.hospital.domain.WeeklyType.SUNDAY;
import static live.allone.hospital.domain.WeeklyType.THURSDAY;
import static live.allone.hospital.domain.WeeklyType.TUESDAY;
import static live.allone.hospital.domain.WeeklyType.WEDNESDAY;

import jakarta.persistence.Column;
import java.time.format.DateTimeFormatter;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public boolean isOperational(WeeklyType weeklyType) throws RuntimeException {
        if (MONDAY.equals(weeklyType)) {
            return monOpen != null
                && !monOpen.isBlank()
                && monClose != null
                && !monClose.isBlank();
        }
        if (TUESDAY.equals(weeklyType)) {
            return tueOpen != null
                && !tueOpen.isBlank()
                && tueClose != null
                && !tueClose.isBlank();
        }
        if (WEDNESDAY.equals(weeklyType)) {
            return wedOpen != null
                && !wedOpen.isBlank()
                && wedClose != null
                && !wedClose.isBlank();
        }
        if (THURSDAY.equals(weeklyType)) {
            return thuOpen != null
                && !thuOpen.isBlank()
                && thuClose != null
                && !thuClose.isBlank();
        }
        if (FRIDAY.equals(weeklyType)) {
            return friOpen != null
                && !friOpen.isBlank()
                && friClose != null
                && !friClose.isBlank();
        }
        if (SATURDAY.equals(weeklyType)) {
            return satOpen != null
                && !satOpen.isBlank()
                && satClose != null
                && !satClose.isBlank();
        }
        if (SUNDAY.equals(weeklyType)) {
            return sunOpen != null
                && !sunOpen.isBlank()
                && sunClose != null
                && !sunClose.isBlank();
        }
        if (HOLIDAY.equals(weeklyType)) {
            return holidayOpen != null
                && !holidayOpen.isBlank()
                && holidayClose != null
                && !holidayClose.isBlank();
        }

        throw new IllegalArgumentException("잘못된 요일 구분입니다.");
    }

    public String getOpen(WeeklyType weeklyType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

        if (MONDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getMonOpen());
        }
        if (TUESDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getTueOpen());
        }
        if (WEDNESDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getWedOpen());
        }
        if (THURSDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getThuOpen());
        }
        if (FRIDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getFriOpen());
        }
        if (SATURDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getSatOpen());
        }
        if (SUNDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getSunOpen());
        }
        if (HOLIDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getHolidayOpen());
        }

        return "";
    }

    public String getClose(WeeklyType weeklyType) {

        if (MONDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getMonClose());
        }
        if (TUESDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getTueClose());
        }
        if (WEDNESDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getWedClose());
        }
        if (THURSDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getThuClose());
        }
        if (FRIDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getFriClose());
        }
        if (SATURDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getSatClose());
        }
        if (SUNDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getSunClose());
        }
        if (HOLIDAY.equals(weeklyType) && isOperational(weeklyType)) {
            return format(getHolidayClose());
        }

        return "";
    }

    private String format(String time) {
        return time.substring(0, 2) + ":" + time.substring(2);
    }
}
