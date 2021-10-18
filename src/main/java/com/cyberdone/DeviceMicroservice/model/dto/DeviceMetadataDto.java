package com.cyberdone.DeviceMicroservice.model.dto;

import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_BLANK_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_NULL_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_NOT_NUMBER_MSG;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMetadataDto {
    @NotNull(message = VALUE_IS_NULL_MSG)
    @Digits(message = VALUE_NOT_NUMBER_MSG, integer = Integer.MAX_VALUE, fraction = 10)
    private Long id;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String uuid;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String name;
    @NotBlank(message = VALUE_IS_BLANK_MSG)
    private String description;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private DeviceType deviceType;
    @NotNull(message = VALUE_IS_NULL_MSG)
    private Boolean accessEnabled;
}
