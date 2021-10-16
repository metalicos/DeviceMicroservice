package com.cyberdone.DeviceMicroservice.persistence.repository;

import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceSpecialInformation;
import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceSpecialInformationRepository extends JpaRepository<DeviceSpecialInformation, Long> {

    @Query("select s from DeviceSpecialInformation s where s.uuid = :uuid order by s.receiveTime desc ")
    List<HydroponicData> findLastInformation(@Param("uuid") String uuid,
                                             Pageable pageable);
}