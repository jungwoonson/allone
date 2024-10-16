package live.allone.scraper.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HospitalBody {

    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private List<HospitalItem> items;
}
