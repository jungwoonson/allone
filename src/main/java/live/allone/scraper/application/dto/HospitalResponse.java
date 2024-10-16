package live.allone.scraper.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HospitalResponse {

    private HospitalHeader header;
    private HospitalBody body;

    public String getResultCode() {
        return header.getResultCode();
    }

    public String getResultMsg() {
        return header.getResultMsg();
    }

    public int getNumOfRows() {
        return body.getNumOfRows();
    }

    public int getPageNo() {
        return body.getPageNo();
    }

    public int getTotalCount() {
        return body.getTotalCount();
    }

    public List<HospitalItem> getItems() {
        return body.getItems();
    }
}
