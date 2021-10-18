package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.exception.NotFoundException;
import com.cyberdone.DeviceMicroservice.model.dto.DeviceMetadataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceMetadata;
import com.cyberdone.DeviceMicroservice.persistence.repository.DeviceMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceMetadataService {
    private final ModelMapper modelMapper;
    private final DeviceMetadataRepository deviceMetadataRepository;

    @Transactional
    public DeviceMetadataDto updateMetadata(String uuid, String name, String description) {
        var meta = deviceMetadataRepository.findByUuid(uuid).orElseThrow(
                () -> new NotFoundException("Device Metadata Not Found for uuid=" + uuid));
        meta.setName(name);
        meta.setDescription(description);
        return modelMapper.map(deviceMetadataRepository.save(meta), DeviceMetadataDto.class);
    }

    public boolean isEnabled(String uuid) {
        return deviceMetadataRepository.isEnabled(uuid);
    }

    public DeviceMetadataDto getMetadataByUuid(String uuid) {
        return modelMapper.map(
                deviceMetadataRepository.findByUuid(uuid).orElse(new DeviceMetadata()), DeviceMetadataDto.class);
    }

    @Transactional
    public void deleteMetadata(String uuid) {
        deviceMetadataRepository.deleteByUuid(uuid);
    }
}
