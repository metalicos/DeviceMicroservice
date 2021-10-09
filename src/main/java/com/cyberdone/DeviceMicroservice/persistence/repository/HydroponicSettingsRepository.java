package com.cyberdone.DeviceMicroservice.persistence.repository;

import com.cyberdone.DeviceMicroservice.persistence.entity.HydroponicSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HydroponicSettingsRepository extends JpaRepository<HydroponicSettings, Long> {

    List<HydroponicSettings> findAllByUuid(String uuid);

    List<HydroponicSettings> findByUuidOrderByMicrocontrollerTimeDesc(String uuid);

    void deleteAllByUuid(String uuid);

    boolean existsByUuid(String uuid);
}