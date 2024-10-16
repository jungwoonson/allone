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
public class Emergency {

    @Column
    private String emergencyInstitutionCode;
    @Column
    private String emergencyInstitutionName;
    @Column
    private String emergencyServiceStatus;
    @Column
    private String emergencyPhoneNumber;

    public Emergency() {
    }
}
