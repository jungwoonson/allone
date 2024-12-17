package live.allone.hospital.domain;

import live.allone.hospital.application.dto.HospitalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HospitalDynamicRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Hospital> findHospitals(HospitalRequest request) {
        StringBuilder sql = new StringBuilder("SELECT" +
                " id," +
                " address," +
                " created_at," +
                " description," +
                " emergency_institution_code," +
                " emergency_institution_name," +
                " emergency_phone_number," +
                " emergency_service_status," +
                " hospital_id," +
                " name," +
                " note," +
                " fri_close," +
                " fri_open," +
                " holiday_close," +
                " holiday_open," +
                " mon_close," +
                " mon_open," +
                " sat_close," +
                " sat_open," +
                " sun_close," +
                " sun_open," +
                " thu_close," +
                " thu_open," +
                " tue_close," +
                " tue_open," +
                " wed_close," +
                " wed_open," +
                " phone_number," +
                " post_code1," +
                " post_code2," +
                " sketch_map," +
                " synchronized_at," +
                " type_code," +
                " type_name," +
                " ST_X(coordinates) AS longitude," +
                " ST_Y(coordinates) AS latitude, " +
                " ST_Distance(coordinates, ST_SetSRID(ST_MakePoint(?, ?), 4326)) AS distance" +
                " FROM hospital" +
                " WHERE 1=1");

        List<Object> paramList = new ArrayList<>();
        paramList.add(request.getLongitude());
        paramList.add(request.getLatitude());

        nameWhereCondition(request, sql, paramList);

        sql.append(" ORDER BY distance ASC");
        sql.append(" LIMIT ? OFFSET ?");
        paramList.add(request.getSize());
        paramList.add(request.getPage() - 1);

        String string = sql.toString();

        return jdbcTemplate.query(string, paramList.toArray(), (rs, rowNum) ->
                Hospital.builder()
                        .id(rs.getLong("id"))
                        .hospitalId(rs.getString("hospital_id"))
                        .name(rs.getString("name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .address(rs.getString("address"))
                        .typeCode(rs.getString("type_code"))
                        .typeName(rs.getString("type_name"))
                        .coordinates(rs.getDouble("longitude"), rs.getDouble("latitude"))
                        .description(rs.getString("description"))
                        .note(rs.getString("note"))
                        .sketchMap(rs.getString("sketch_map"))
                        .postCode1(rs.getString("post_code1"))
                        .postCode2(rs.getString("post_code2"))
                        .emergency(Emergency.builder()
                                .emergencyInstitutionCode(rs.getString("emergency_institution_code"))
                                .emergencyInstitutionName(rs.getString("emergency_institution_name"))
                                .emergencyServiceStatus(rs.getString("emergency_service_status"))
                                .emergencyPhoneNumber(rs.getString("emergency_phone_number"))
                                .build())
                        .operatingHour(OperatingHour.builder()
                                .monOpen(rs.getString("mon_open"))
                                .monClose(rs.getString("mon_close"))
                                .tueOpen(rs.getString("tue_open"))
                                .tueClose(rs.getString("tue_close"))
                                .wedOpen(rs.getString("wed_open"))
                                .wedClose(rs.getString("wed_close"))
                                .thuOpen(rs.getString("thu_open"))
                                .thuClose(rs.getString("thu_close"))
                                .friOpen(rs.getString("fri_open"))
                                .friClose(rs.getString("fri_close"))
                                .satOpen(rs.getString("sat_open"))
                                .satClose(rs.getString("sat_close"))
                                .sunOpen(rs.getString("sun_open"))
                                .sunClose(rs.getString("sun_close"))
                                .holidayOpen(rs.getString("holiday_open"))
                                .holidayClose(rs.getString("holiday_close"))
                                .build())
                        .build()
        );
    }

    public int count(HospitalRequest request) {
        StringBuilder sql = new StringBuilder("SELECT" +
                " COUNT(*)" +
                " FROM hospital" +
                " WHERE 1=1");

        List<Object> paramList = new ArrayList<>();

        nameWhereCondition(request, sql, paramList);

        return jdbcTemplate.queryForObject(sql.toString(), Integer.class, paramList.toArray());
    }

    private void nameWhereCondition(HospitalRequest request, StringBuilder sql, List<Object> paramList) {
        if (request.getName() != null && !request.getName().isBlank()) {
            sql.append(" AND (");
            sql.append(" name like '%' || ? || '%'");
            sql.append(" OR name % ?");
            sql.append(" OR name like '%' || ? || '%'");
            sql.append(" OR name % ?");
            sql.append(")");
            paramList.add(request.getName());
            paramList.add(request.getName());
            String noSpaceName = request.getName().replaceAll(" ", "");
            paramList.add(noSpaceName);
            paramList.add(noSpaceName);
        }
    }
}
