package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.model.dto.HydroponicSettingsDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.HydroponicSettings;
import com.cyberdone.DeviceMicroservice.persistence.repository.HydroponicSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HydroponicSettingsService {
    private final ModelMapper modelMapper;
    private final HydroponicSettingsRepository hydroponicSettingsRepository;

    @Transactional
    public HydroponicSettings saveSetting(HydroponicSettings message) {
        return hydroponicSettingsRepository.save(Optional.ofNullable(message).orElseThrow(
                () -> new IllegalStateException("Message is not valid")));
    }

    public List<HydroponicSettingsDto> getAllSettingsByUuid(String uuid) {
        return hydroponicSettingsRepository.findAllByUuid(checkUUID(uuid)).stream()
                .map(s -> modelMapper.map(s, HydroponicSettingsDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllSettingsByUuid(String uuid) {
        hydroponicSettingsRepository.deleteAllByUuid(checkUUID(uuid));
    }

    public HydroponicSettingsDto getLastSettingByUuid(String uuid) {
        var settingsList = hydroponicSettingsRepository.findByUuidOrderByMicrocontrollerTimeDesc(checkUUID(uuid));
        if (settingsList.isEmpty()) {
            return null;
        }
        return modelMapper.map(settingsList.get(0), HydroponicSettingsDto.class);
    }

    private String checkUUID(String uuid) {
        return Optional.ofNullable(uuid).orElseThrow(() -> new IllegalStateException("UUID is not valid"));
    }
}
