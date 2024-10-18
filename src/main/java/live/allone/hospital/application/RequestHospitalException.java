package live.allone.hospital.application;

import live.allone.hospital.application.dto.HospitalRequest;

public class RequestHospitalException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Request hospital failed with numOfRows: %s, pageNo: %s";

    public RequestHospitalException(HospitalRequest hospitalRequest) {
        super(String.format(MESSAGE_FORMAT, hospitalRequest.getNumOfRows(), hospitalRequest.getPageNo()));
    }
}
