package live.allone.hospital.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Optional<Hospital> findByHospitalId(String hospitalId);

    List<Hospital> findBySynchronizedAtBefore(LocalDateTime localDateTime);

    @Modifying
    @Query("UPDATE Hospital h SET h.synchronizedAt = :synchronizedAt")
    @Transactional
    void updateSynchronizedAt(@Param("synchronizedAt") LocalDateTime synchronizedAt);

    @Query(value = "SELECT *, " +
        "ST_Distance(coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) AS distance " +
        "FROM hospital" +
        "ORDER BY distance ASC " +
        "LIMIT :pageSize OFFSET :pageOffset",
        nativeQuery = true)
    List<Hospital> findHospitalsByProximity(
        @Param("longitude") double longitude,
        @Param("latitude") double latitude,
        @Param("pageOffset") int pageOffset,
        @Param("pageSize") int size);
}
