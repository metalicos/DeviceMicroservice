package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.model.dto.internal.HydroponicCalibrationDataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicCalibrationData;
import com.cyberdone.DeviceMicroservice.persistence.repository.HydroponicCalibrationDataRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_NULL_MSG;
import static java.time.temporal.ChronoField.SECOND_OF_DAY;

@Service
@RequiredArgsConstructor
public class HydroponicCalibrationDataService {
    private final ModelMapper modelMapper;
    private final HydroponicCalibrationDataRepository calibrationDataRepository;

    @Transactional
    public void saveCalibrationData(@NotNull(message = VALUE_IS_NULL_MSG) HydroponicCalibrationData data) {
        calibrationDataRepository.save(data);
    }

    @Transactional
    public void deleteCalibrationById(Long id) {
        calibrationDataRepository.deleteById(id);
    }

    public List<HydroponicCalibrationDataDto> getLastCalibrationByUuid(String uuid, int page, int limit) {
        return calibrationDataRepository.findLastData(uuid, PageRequest.of(page, limit)).stream()
                .map(d -> modelMapper.map(d, HydroponicCalibrationDataDto.class))
                .sorted(Comparator.comparingLong(v -> v.getMicrocontrollerTime().getLong(SECOND_OF_DAY)))
                .collect(Collectors.toList());
    }
}
