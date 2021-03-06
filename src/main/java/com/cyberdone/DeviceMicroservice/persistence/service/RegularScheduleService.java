package com.cyberdone.DeviceMicroservice.persistence.service;

import com.cyberdone.DeviceMicroservice.model.dto.RegularScheduleDto;
import com.cyberdone.DeviceMicroservice.model.dto.RegularScheduleUpdateDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.RegularSchedule;
import com.cyberdone.DeviceMicroservice.persistence.repository.RegularScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegularScheduleService {
    private final ModelMapper modelMapper;
    private final RegularScheduleRepository regularScheduleRepository;

    @Transactional
    public RegularScheduleDto saveSchedule(RegularScheduleDto scheduleRequest) {
        var schedule = modelMapper.map(scheduleRequest, RegularSchedule.class);
        return modelMapper.map(regularScheduleRepository.save(Optional.ofNullable(schedule).orElseThrow(
                () -> new IllegalStateException("Schedule is not valid"))), RegularScheduleDto.class);
    }

    @Transactional
    public RegularScheduleDto updateSchedule(RegularScheduleUpdateDto scheduleRequest) {
        RegularSchedule schedule = regularScheduleRepository.findById(scheduleRequest.getId())
                .orElseThrow(() -> new IllegalStateException("Schedule not found"));
        schedule.setName(scheduleRequest.getName());
        schedule.setDescription(scheduleRequest.getDescription());
        return modelMapper.map(regularScheduleRepository.save(schedule), RegularScheduleDto.class);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        regularScheduleRepository.deleteById(id);
    }

    public List<RegularScheduleDto> getAllSchedulesWithDays(boolean monday, boolean tuesday, boolean wednesday,
                                                            boolean thursday, boolean friday, boolean saturday,
                                                            boolean sunday) {
        return regularScheduleRepository.findByDays(monday, tuesday, wednesday, thursday, friday, saturday, sunday)
                .stream()
                .map(s -> modelMapper.map(s, RegularScheduleDto.class))
                .collect(Collectors.toList());
    }

    public List<RegularScheduleDto> getScheduleByUuidAndMetadata(String uuid, String key) {
        return regularScheduleRepository.findAllByUuidAndKey(uuid, key)
                .stream()
                .map(e -> modelMapper.map(e, RegularScheduleDto.class))
                .collect(Collectors.toList());
    }
}
