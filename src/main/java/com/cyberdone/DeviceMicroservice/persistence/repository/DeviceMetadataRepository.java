package com.cyberdone.DeviceMicroservice.persistence.repository;


import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    Optional<DeviceMetadata> findByUuid(String uuid);

    @Query("select m.accessEnabled from DeviceMetadata m where m.uuid = :uuid")
    boolean isEnabled(@Param("uuid") String uuid);

    void deleteByUuid(String uuid);
}