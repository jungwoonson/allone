package live.allone.hospital.application.dto;

import live.allone.hospital.domain.Emergency;
import live.allone.hospital.domain.Hospital;
import live.allone.hospital.domain.OperatingHour;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class HospitalSyncItem {

    private String rnum; // 일련번호
    private String dutyAddr; // 주소
    private String dutyDiv; // 병원분류
    private String dutyDivNam; // 병원분류명
    private String dutyEmcls; // 응급의료기관코드
    private String dutyEmclsName; // 응급의료기관코드명
    private String dutyEryn; // 응급운영여부
    private String dutyEtc; // 비고
    private String dutyInf; // 기관설명상세
    private String dutyMapimg; // 같이약도
    private String dutyName; // 기관명
    private String dutyTel1; // 대표전화1
    private String dutyTel3; //응급실전화
    private String dutyTime1c; // 진료시간(월요일)C
    private String dutyTime2c; // 진료시간(화요일)C
    private String dutyTime3c; // 진료시간(수요일)C
    private String dutyTime4c; // 진료시간(목요일)C
    private String dutyTime5c; // 진료시간(금요일)C
    private String dutyTime6c; // 진료시간(토요일)C
    private String dutyTime7c; // 진료시간(일요일)C
    private String dutyTime8c; // 진료시간(공휴일)C
    private String dutyTime1s; // 진료시간(월요일)S
    private String dutyTime2s; // 진료시간(화요일)S
    private String dutyTime3s; // 진료시간(수요일)S
    private String dutyTime4s; // 진료시간(목요일)S
    private String dutyTime5s; // 진료시간(금요일)S
    private String dutyTime6s; // 진료시간(토요일)S
    private String dutyTime7s; // 진료시간(일요일)S
    private String dutyTime8s; // 진료시간(공휴일)S
    private String hpid; // 기관ID
    private String postCdn1; // 우편번호1
    private String postCdn2; // 우편번호2
    private String wgs84Lon; // 병원경도
    private String wgs84Lat; // 병원위도

    public Hospital createHospital() {
        return Hospital.builder()
            .hospitalId(hpid)
            .name(dutyName)
            .phoneNumber(dutyTel1)
            .address(dutyAddr)
            .typeCode(dutyDiv)
            .typeName(dutyDivNam)
            .coordinates(wgs84Lon, wgs84Lat)
            .description(dutyInf)
            .note(dutyEtc)
            .sketchMap(dutyMapimg)
            .postCode1(postCdn1)
            .postCode2(postCdn2)
            .emergency(buildEmergency())
            .operatingHour(buildOperatingHour())
            .build();
    }

    private Emergency buildEmergency() {
        return Emergency.builder()
            .emergencyInstitutionCode(dutyEmcls)
            .emergencyInstitutionName(dutyEmclsName)
            .emergencyServiceStatus(dutyEryn)
            .emergencyPhoneNumber(dutyTel3)
            .build();
    }

    private OperatingHour buildOperatingHour() {
        return OperatingHour.builder()
            .monOpen(dutyTime1s)
            .monClose(dutyTime1c)
            .tueOpen(dutyTime2s)
            .tueClose(dutyTime2c)
            .wedOpen(dutyTime3s)
            .wedClose(dutyTime3c)
            .thuOpen(dutyTime4s)
            .thuClose(dutyTime4c)
            .friOpen(dutyTime5s)
            .friClose(dutyTime5c)
            .satOpen(dutyTime6s)
            .satClose(dutyTime6c)
            .sunOpen(dutyTime7s)
            .sunClose(dutyTime7c)
            .holidayOpen(dutyTime8s)
            .holidayClose(dutyTime8c)
            .build();
    }
}
