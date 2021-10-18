package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.model.dto.microcontrollers.hydroponic.HydroponicDataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceSpecialInformation;
import com.cyberdone.DeviceMicroservice.persistence.repository.DeviceSpecialInformationRepository;
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
public class DeviceSpecialInformationService {
    private final ModelMapper modelMapper;
    private final DeviceSpecialInformationRepository specialInformationRepository;

    @Transactional
    public void saveSpecialInformation(
            @NotNull(message = VALUE_IS_NULL_MSG) DeviceSpecialInformation specialInformation) {
        specialInformationRepository.save(specialInformation);
    }

    @Transactional
    public void deleteDataById(Long id) {
        specialInformationRepository.deleteById(id);
    }

    public List<HydroponicDataDto> getLastDataByUuid(String uuid, int page, int limit) {
        return specialInformationRepository.findLastInformation(uuid, PageRequest.of(page, limit)).stream()
                .map(d -> modelMapper.map(d, HydroponicDataDto.class))
                .sorted(Comparator.comparingLong(v -> v.getMicrocontrollerTime().getLong(SECOND_OF_DAY)))
                .collect(Collectors.toList());
    }
}
