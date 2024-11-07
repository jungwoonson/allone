package live.allone.hospital.application;

import live.allone.hospital.application.dto.HospitalSyncRequest;

public class RequestHospitalException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Request hospital failed with numOfRows: %s, pageNo: %s";

    public RequestHospitalException(HospitalSyncRequest hospitalSyncRequest) {
        super(String.format(MESSAGE_FORMAT, hospitalSyncRequest.getNumOfRows(), hospitalSyncRequest.getPageNo()));
    }
}
