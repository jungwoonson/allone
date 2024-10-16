package live.allone.scraper.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HospitalItem {

    private String rnum; // 일련번호
    private String autyAddr; // 주소
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
}
