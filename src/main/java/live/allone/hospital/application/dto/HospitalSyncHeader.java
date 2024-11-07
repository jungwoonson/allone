package live.allone.hospital.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalSyncHeader {

    private String resultCode;
    private String resultMsg;
}
