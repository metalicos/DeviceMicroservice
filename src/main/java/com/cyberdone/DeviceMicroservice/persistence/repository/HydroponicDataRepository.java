package com.cyberdone.DeviceMicroservice.persistence.repository;


import com.cyberdone.DeviceMicroservice.persistence.entity.HydroponicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HydroponicDataRepository extends JpaRepository<HydroponicData, Long> {

    List<HydroponicData> findByUuid(String uuid);

    List<HydroponicData> findByUuidOrderByMicrocontrollerTimeDesc(String uuid);

    List<HydroponicData> findByUuidOrderByReceiveTimeDesc(String uuid);

    void deleteByUuid(String uuid);

    void deleteByUuidAndMicrocontrollerTimeAfter(String uuid, Date time);

    void deleteByUuidAndMicrocontrollerTimeBefore(String uuid, Date time);

    @Query("delete from HydroponicData p where p.uuid = :uuid and p.microcontrollerTime > :fromTime and p.microcontrollerTime < :toTime")
    void deleteByUuidInRange(@Param("uuid") String uuid,
                             @Param("fromTime") Date fromTime,
                             @Param("toTime") Date toTime);

    @Query("select AVG(p.phValue) from HydroponicData p where p.uuid = :uuid")
    Double averagePhByUuid(@Param("uuid") String uuid);

    @Query("select AVG(p.phValue) from HydroponicData p where p.uuid = :uuid " +
            "and p.microcontrollerTime > :fromTime " +
            "and p.microcontrollerTime < :toTime")
    Double averagePhByUuid(@Param("uuid") String uuid,
                           @Param("fromTime") Date fromTime,
                           @Param("toTime") Date toTime);

    @Query("select AVG(p.ecValue) from HydroponicData p where p.uuid = :uuid")
    Double averageEcByUuid(@Param("uuid") String uuid);

    @Query("select AVG(p.ecValue) from HydroponicData p where p.uuid = :uuid " +
            "and p.microcontrollerTime > :fromTime " +
            "and p.microcontrollerTime < :toTime")
    Double averageEcByUuid(@Param("uuid") String uuid,
                           @Param("fromTime") Date fromTime,
                           @Param("toTime") Date toTime);

    @Query("select AVG(p.temperatureValue) from HydroponicData p where p.uuid = :uuid")
    Double averageTemperatureByUuid(@Param("uuid") String uuid);

    @Query("select AVG(p.temperatureValue) from HydroponicData p where p.uuid = :uuid " +
            "and p.microcontrollerTime > :fromTime " +
            "and p.microcontrollerTime < :toTime")
    Double averageTemperatureByUuid(@Param("uuid") String uuid,
                                    @Param("fromTime") Date fromTime,
                                    @Param("toTime") Date toTime);

    @Query("select AVG(p.tdsValue) from HydroponicData p where p.uuid = :uuid")
    Double averageTdsByUuid(@Param("uuid") String uuid);

    @Query("select AVG(p.tdsValue) from HydroponicData p where p.uuid = :uuid " +
            "and p.microcontrollerTime > :fromTime " +
            "and p.microcontrollerTime < :toTime")
    Double averageTdsByUuid(@Param("uuid") String uuid,
                            @Param("fromTime") Date fromTime,
                            @Param("toTime") Date toTime);
}
