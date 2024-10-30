package live.allone.hospital.application;

import live.allone.hospital.application.dto.HospitalItem;
import live.allone.hospital.domain.Hospital;
import live.allone.hospital.domain.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalUpdater {

    private HospitalRepository hospitalRepository;

    public HospitalUpdater(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
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
            existing.updateAll(item.createHospital());
            return existing;
        }
        return item.createHospital();
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
}
