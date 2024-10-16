package live.allone.hospital.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import live.allone.hospital.application.dto.HospitalItem;
import live.allone.hospital.domain.Emergency;
import live.allone.hospital.domain.Hospital;
import live.allone.hospital.domain.HospitalRepository;
import live.allone.hospital.domain.OperatingHour;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HospitalUpdater {

    private HospitalRepository hospitalRepository;

    public HospitalUpdater(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional
    public void saveAll(List<HospitalItem> hospitals) {
        List<Hospital> savedHospitals = new ArrayList<>();
        for (HospitalItem item : hospitals) {
            Optional<Hospital> existingHospital = hospitalRepository.findByHospitalId(item.getHpid());
            savedHospitals.add(getHospitalForUpdate(item, existingHospital));
        }
        hospitalRepository.saveAll(savedHospitals);
    }

    private Hospital getHospitalForUpdate(HospitalItem item, Optional<Hospital> existingHospital) {
        if (existingHospital.isPresent()) {
            Hospital existing = existingHospital.get();
            existing.updateAll(createHospitalFromItem(item));
            return existing;
        }
        return createHospitalFromItem(item);
    }

    @Transactional
    public void deleteNotUpdatedHospitals() {
        LocalDate today = LocalDate.now();
        LocalDateTime localDateTime = today.atStartOfDay();
        List<Hospital> hospitalsToDelete = hospitalRepository.findBySynchronizedAtBefore(localDateTime);

        if (!hospitalsToDelete.isEmpty()) {
            hospitalRepository.deleteAll(hospitalsToDelete);
        }
    }

    private Hospital createHospitalFromItem(HospitalItem hospitalItem) {
        return Hospital.builder()
            .hospitalId(hospitalItem.getHpid())
            .name(hospitalItem.getDutyName())
            .phoneNumber(hospitalItem.getDutyTel1())
            .address(hospitalItem.getDutyAddr())
            .typeCode(hospitalItem.getDutyDiv())
            .typeName(hospitalItem.getDutyDivNam())
            .latitude(hospitalItem.getWgs84Lat())
            .longitude(hospitalItem.getWgs84Lon())
            .description(hospitalItem.getDutyInf())
            .note(hospitalItem.getDutyEtc())
            .sketchMap(hospitalItem.getDutyMapimg())
            .postCode1(hospitalItem.getPostCdn1())
            .postCode2(hospitalItem.getPostCdn2())
            .emergency(buildEmergency(hospitalItem))
            .operatingHour(buildOperatingHour(hospitalItem))
            .build();
    }

    private Emergency buildEmergency(HospitalItem hospitalItem) {
        return Emergency.builder()
            .emergencyInstitutionCode(hospitalItem.getDutyEmcls())
            .emergencyInstitutionName(hospitalItem.getDutyEmclsName())
            .emergencyServiceStatus(hospitalItem.getDutyEryn())
            .emergencyPhoneNumber(hospitalItem.getDutyTel3())
            .build();
    }

    private OperatingHour buildOperatingHour(HospitalItem hospitalItem) {
        return OperatingHour.builder()
            .monOpen(hospitalItem.getDutyTime1s())
            .monClose(hospitalItem.getDutyTime1c())
            .tueOpen(hospitalItem.getDutyTime2s())
            .tueClose(hospitalItem.getDutyTime2c())
            .wedOpen(hospitalItem.getDutyTime3s())
            .wedClose(hospitalItem.getDutyTime3c())
            .thuOpen(hospitalItem.getDutyTime4s())
            .thuClose(hospitalItem.getDutyTime4c())
            .friOpen(hospitalItem.getDutyTime5s())
            .friClose(hospitalItem.getDutyTime5c())
            .satOpen(hospitalItem.getDutyTime6s())
            .satClose(hospitalItem.getDutyTime6c())
            .sunOpen(hospitalItem.getDutyTime7s())
            .sunClose(hospitalItem.getDutyTime7c())
            .holidayOpen(hospitalItem.getDutyTime8s())
            .holidayClose(hospitalItem.getDutyTime8c())
            .build();
    }
}
