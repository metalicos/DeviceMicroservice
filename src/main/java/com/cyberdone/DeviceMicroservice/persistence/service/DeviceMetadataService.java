package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.exception.AlreadyExistException;
import com.cyberdone.DeviceMicroservice.exception.NotFoundException;
import com.cyberdone.DeviceMicroservice.model.dto.DeviceMetadataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceMetadata;
import com.cyberdone.DeviceMicroservice.persistence.repository.DeviceMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceMetadataService {
    private final ModelMapper modelMapper;
    private final DeviceMetadataRepository deviceMetadataRepository;

    @Transactional
    public DeviceMetadataDto saveMetadata(DeviceMetadataDto dto) {
        if (!deviceMetadataRepository.existsByUuid(dto.getUuid())) {
            var saved = deviceMetadataRepository.save(modelMapper.map(dto, DeviceMetadata.class));
            return modelMapper.map(deviceMetadataRepository.save(saved), DeviceMetadataDto.class);
        }
        throw new AlreadyExistException("Device already exists. chose another UUID.");
    }

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

    public List<DeviceMetadataDto> getMetadataByUser(Long userId) {
        return deviceMetadataRepository.findAllByUserId(userId).stream()
                .map(metadata -> modelMapper.map(metadata, DeviceMetadataDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMetadata(String uuid) {
        deviceMetadataRepository.deleteByUuid(uuid);
    }

    @Transactional
    public void unlinkMetadataFromUser(String uuid) {
        if (deviceMetadataRepository.existsByUuid(uuid)) {
            deviceMetadataRepository.unlinkDeviceMetadataFromUser(uuid);
        }
    }

    @Transactional
    public void linkMetadataToUser(String uuid, long userId) {
        if (deviceMetadataRepository.existsByUuid(uuid)) {
            deviceMetadataRepository.linkDeviceMetadataToUser(uuid, userId);
        }
    }
}
