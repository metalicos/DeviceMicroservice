package com.cyberdone.DeviceMicroservice.persistence.repository;


import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HydroponicDataRepository extends JpaRepository<HydroponicData, Long> {

    @Query("select d from HydroponicData d where d.uuid = :uuid order by d.receiveTime desc ")
    List<HydroponicData> findLastData(@Param("uuid") String uuid,
                                      Pageable pageable);
}
