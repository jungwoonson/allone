package live.allone.hospital.unit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.stream.Stream;
import live.allone.hospital.domain.Hospital;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@DisplayName("병원 단위 테스트")
public class HospitalTest {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();
    private static final double LON = 127.001699;
    private static final double LAT = 37.564214;

    @DisplayName("거리 비교 함수는, 주어진 지점을 기준으로 가장 가까운 병원이 본인이면 -1을 반환하고 같으면 0을 반환하고 아니면 1을 반환한다.")
    @ParameterizedTest
    @MethodSource("hospitalCompareParameters")
    void compareDistance(double lon, double lat, double otherLon, double otherLat, int expected) {
        // given
        Point referencePoint = GEOMETRY_FACTORY.createPoint(new Coordinate(LON, LAT));
        Hospital hospital = createHospitalFrom(lon, lat);
        Hospital otherHospital = createHospitalFrom(otherLon, otherLat);

        // when
        int actual = hospital.compareTo(otherHospital, referencePoint);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Hospital createHospitalFrom(double lat, double lon) {
        return Hospital.builder()
            .coordinates(lon, lat)
            .build();
    }

    private static Stream<Arguments> hospitalCompareParameters() {
        double adjustment = 0.00001;

        return Stream.of(
            Arguments.of(LON, LAT, LON - adjustment, LAT - adjustment, -1),
            Arguments.of(LON, LAT + adjustment, LON, LAT + adjustment, 0),
            Arguments.of(LON + adjustment, LAT, LON, LAT, 1)
        );
    }

    @DisplayName("거리 계산 함수는, 주어진 지점과 병원의 거리를 계산한다.")
    @Test
    void calculateDistance() {
        // given
        Hospital source = Hospital.builder()
            .coordinates(126.978526, 37.566510)
            .build();
        Hospital target = Hospital.builder()
            .coordinates(126.979337, 37.565880)
            .build();
        int meter100 = 100;

        // when
        int distance = source.calculateDistance(target);

        // then
        assertThat(distance).isEqualTo(meter100);
    }
}
