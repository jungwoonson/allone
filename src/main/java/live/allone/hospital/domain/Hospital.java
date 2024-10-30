package live.allone.hospital.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Hospital {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String hospitalId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String typeCode;
    @Column(nullable = false)
    private String typeName;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime synchronizedAt;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point coordinates;
    @Column(length = 2000)
    private String description;
    @Column(length = 2000)
    private String note;
    @Column
    private String sketchMap;
    @Column
    private String postCode1;
    @Column
    private String postCode2;

    @Embedded
    private Emergency emergency;
    @Embedded
    private OperatingHour operatingHour;

    public Hospital() {
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.synchronizedAt = LocalDateTime.now();
    }

    public void updateAll(Hospital hospital) {
        this.name = hospital.getName();
        this.phoneNumber = hospital.getPhoneNumber();
        this.address = hospital.getAddress();
        this.typeCode = hospital.getTypeCode();
        this.typeName = hospital.getTypeName();
        this.description = hospital.getDescription();
        this.note = hospital.getNote();
        this.sketchMap = hospital.getSketchMap();
        this.postCode1 = hospital.getPostCode1();
        this.postCode2 = hospital.getPostCode2();
        this.emergency = hospital.getEmergency();
        this.operatingHour = hospital.getOperatingHour();
        this.synchronizedAt = LocalDateTime.now();
        this.coordinates = hospital.getCoordinates();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o.getClass();
        if (o instanceof HibernateProxy) {
            oEffectiveClass = ((HibernateProxy) o)
                    .getHibernateLazyInitializer()
                    .getPersistentClass();
        }
        Class<?> thisEffectiveClass = this.getClass();
        if (this instanceof HibernateProxy) {
            thisEffectiveClass = ((HibernateProxy) this)
                    .getHibernateLazyInitializer()
                    .getPersistentClass();
        }
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        Hospital hospital = (Hospital) o;
        return getId() != null && Objects.equals(getId(), hospital.getId());
    }

    @Override
    public final int hashCode() {
        int hashCode = getClass()
                .hashCode();
        if (this instanceof HibernateProxy) {
            hashCode = ((HibernateProxy) this)
                    .getHibernateLazyInitializer()
                    .getPersistentClass()
                    .hashCode();
        }
        return hashCode;
    }

    public static class HospitalBuilder {
        public HospitalBuilder coordinates(String longitude, String latitude) {
            if (latitude == null || longitude == null) {
                return this;
            }
            return coordinates(Double.parseDouble(longitude), Double.parseDouble(latitude));
        }

        public HospitalBuilder coordinates(double longitude, double latitude) {
            Coordinate coordinate = new Coordinate(longitude, latitude);
            this.coordinates = GEOMETRY_FACTORY.createPoint(coordinate);
            return this;
        }
    }
}
