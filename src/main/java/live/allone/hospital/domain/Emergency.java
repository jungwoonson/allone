package live.allone.hospital.domain;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Emergency {

    @Column
    private String emergencyInstitutionCode;
    @Column
    private String emergencyInstitutionName;
    @Column
    private String emergencyServiceStatus;
    @Column
    private String emergencyPhoneNumber;
}
