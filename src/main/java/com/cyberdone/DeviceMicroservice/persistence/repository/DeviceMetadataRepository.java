package com.cyberdone.DeviceMicroservice.persistence.repository;


import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    Optional<DeviceMetadata> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    boolean existsByUuid(String uuid);
}