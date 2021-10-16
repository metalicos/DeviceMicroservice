package com.cyberdone.DeviceMicroservice.persistence.repository;

import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicData;
import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicSettings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HydroponicSettingsRepository extends JpaRepository<HydroponicSettings, Long> {

    @Query("select s from HydroponicSettings s where s.uuid = :uuid order by s.receiveTime desc ")
    List<HydroponicData> findLastSettings(@Param("uuid") String uuid,
                                          Pageable pageable);
}