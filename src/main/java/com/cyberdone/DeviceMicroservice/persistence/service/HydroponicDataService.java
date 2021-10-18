package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.model.dto.microcontrollers.hydroponic.HydroponicDataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicData;
import com.cyberdone.DeviceMicroservice.persistence.repository.HydroponicDataRepository;
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
public class HydroponicDataService {
    private final ModelMapper modelMapper;
    private final HydroponicDataRepository hydroponicDataRepository;

    @Transactional
    public void saveData(@NotNull(message = VALUE_IS_NULL_MSG) HydroponicData data) {
        hydroponicDataRepository.save(data);
    }

    @Transactional
    public void deleteDataById(Long id) {
        hydroponicDataRepository.deleteById(id);
    }

    public List<HydroponicDataDto> getLastDataByUuid(String uuid, int page, int limit) {
        return hydroponicDataRepository.findLastData(uuid, PageRequest.of(page, limit)).stream()
                .map(d -> modelMapper.map(d, HydroponicDataDto.class))
                .sorted(Comparator.comparingLong(v -> v.getMicrocontrollerTime().getLong(SECOND_OF_DAY)))
                .collect(Collectors.toList());
    }
}
