package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.exception.NotFoundException;
import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceMetadata;
import com.cyberdone.DeviceMicroservice.persistence.repository.DeviceMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceMetadataService {
    private final DeviceMetadataRepository deviceMetadataRepository;

    @Transactional
    public DeviceMetadata updateMetadata(String uuid, String name, String description) {
        var meta = deviceMetadataRepository.findByUuid(uuid).orElseThrow(
                () -> new NotFoundException("Device Metadata Not Found for uuid=" + uuid));
        meta.setName(name);
        meta.setDescription(description);
        return deviceMetadataRepository.save(meta);
    }

    public boolean isEnabled(String uuid) {
        return deviceMetadataRepository.isEnabled(uuid);
    }

    public DeviceMetadata getMetadataByUuid(String uuid) {
        return deviceMetadataRepository.findByUuid(uuid).orElse(new DeviceMetadata());
    }

    @Transactional
    public void deleteMetadata(String uuid) {
        deviceMetadataRepository.deleteByUuid(uuid);
    }
}
