package com.cyberdone.DeviceMicroservice.model.dto;

import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_BLANK_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_NULL_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_NOT_NUMBER_MSG;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegularScheduleDto {
    @NotNull(message = VALUE_IS_NULL_MSG)
    @Digits(message = VALUE_NOT_NUMBER_MSG, integer = Integer.MAX_VALUE, fraction = 10)
    private Long id;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String uuid;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String name;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String description;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String key;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String topic;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean monday;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean tuesday;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean wednesday;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean thursday;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean friday;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean saturday;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean sunday;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private LocalTime time;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String value;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private ValueType valueType;
}
