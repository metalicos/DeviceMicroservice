package com.cyberdone.DeviceMicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HydroponicDataDto {
    private String uuid;
    private LocalDateTime receiveTime;
    private Double phValue;
    private Double temperatureValue;
    private Double ecValue;
    private Integer tdsValue;
    private Date microcontrollerTime;
}
