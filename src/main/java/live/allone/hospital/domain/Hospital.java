package live.allone.hospital.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class Hospital {

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
    private String latitude;
    @Column(nullable = false)
    private String longitude;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime synchronizedAt;

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
        this.latitude = hospital.getLatitude();
        this.longitude = hospital.getLongitude();
        this.description = hospital.getDescription();
        this.note = hospital.getNote();
        this.sketchMap = hospital.getSketchMap();
        this.postCode1 = hospital.getPostCode1();
        this.postCode2 = hospital.getPostCode2();
        this.emergency = hospital.getEmergency();
        this.operatingHour = hospital.getOperatingHour();
        this.synchronizedAt = LocalDateTime.now();
    }
}
