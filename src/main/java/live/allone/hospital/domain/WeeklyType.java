package live.allone.hospital.domain;

public enum WeeklyType {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7),
    HOLIDAY(8);

    private final int value;

    WeeklyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
