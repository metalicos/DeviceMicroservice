package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.model.dto.HydroponicDataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.HydroponicData;
import com.cyberdone.DeviceMicroservice.persistence.repository.HydroponicDataRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HydroponicDataService {
    private final ModelMapper modelMapper;
    private final HydroponicDataRepository hydroponicDataRepository;

    @Transactional
    public void saveData(HydroponicData data) {
        hydroponicDataRepository.save(Optional.ofNullable(data).orElseThrow(
                () -> new IllegalStateException("Message is not valid")));
    }

    public List<HydroponicDataDto> getAllDataByUuid(String uuid) {
        return hydroponicDataRepository.findByUuid(checkUUID(uuid)).stream()
                .map(d -> modelMapper.map(d, HydroponicDataDto.class))
                .collect(Collectors.toList());
    }

    public HydroponicDataDto getLastDataByUuid(String uuid) {
        var hydroponicDataList = Optional.ofNullable(hydroponicDataRepository.findByUuidOrderByReceiveTimeDesc(
                checkUUID(uuid))).orElseThrow(() -> new IllegalStateException("Hydroponic Data Not Found"));
        if (hydroponicDataList.isEmpty()) {
            return null;
        }
        return modelMapper.map(hydroponicDataList.get(0), HydroponicDataDto.class);
    }

    public List<HydroponicDataDto> getLastLimitedDataByUuid(String uuid, int limit) {
        return hydroponicDataRepository.findByUuidOrderByMicrocontrollerTimeDesc(checkUUID(uuid)).stream()
                .map(d -> modelMapper.map(d, HydroponicDataDto.class))
                .limit(limit)
                .sorted(Comparator.comparingLong(value -> value.getMicrocontrollerTime().getTime()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDataByUuid(String uuid) {
        hydroponicDataRepository.deleteByUuid(checkUUID(uuid));
    }

    public HydroponicDataDto getAverageDataByUuid(String uuid) {
        var ph = hydroponicDataRepository.averagePhByUuid(checkUUID(uuid));
        var ec = hydroponicDataRepository.averageEcByUuid(checkUUID(uuid));
        var tmp = hydroponicDataRepository.averageTemperatureByUuid(checkUUID(uuid));
        var tds = hydroponicDataRepository.averageTdsByUuid(checkUUID(uuid));
        return modelMapper.map(
                new HydroponicData(1L, LocalDateTime.now(), uuid, ph, tmp, ec, tds.intValue(), new Date()),
                HydroponicDataDto.class);
    }

    private String checkUUID(String uuid) {
        return Optional.ofNullable(uuid).orElseThrow(() -> new IllegalStateException("UUID is not valid"));
    }
}
