package com.cyberdone.DeviceMicroservice.model.dto;

import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegularScheduleDto {
    private Long id;
    private String uuid;
    private String name;
    private String description;
    private String key;
    private String topic;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private LocalTime time;
    private String value;
    private ValueType valueType;
}
