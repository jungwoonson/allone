package live.allone.hospital.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class HospitalBody {

    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private List<HospitalItem> items;
}
