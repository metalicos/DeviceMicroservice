package com.cyberdone.DeviceMicroservice.persistence.repository;

import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicCalibrationData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HydroponicCalibrationDataRepository extends JpaRepository<HydroponicCalibrationData, Long> {

    @Query("select d from HydroponicCalibrationData d where d.uuid = :uuid order by d.receiveTime desc ")
    List<HydroponicCalibrationData> findLastData(@Param("uuid") String uuid,
                                                 Pageable pageable);
}
