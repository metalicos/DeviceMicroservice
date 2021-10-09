package com.cyberdone.DeviceMicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HydroponicSettingsDto {
    private String uuid;
    private Double mlPerMillisecond;
    private Double regulateErrorPhUp;
    private Double regulateErrorPhDown;
    private Double regulateErrorFertilizer;
    private Double phUpDoseMl;
    private Double phDownDoseMl;
    private Double fertilizerDoseMl;
    private Long recheckDosatorsAfterMs;
    private Double setupPhValue;
    private Long setupTdsValue;
    private Double setupTemperatureValue;
    private Boolean deviceEnable;
    private Boolean dosatorsEnable;
    private Boolean sensorsEnable;
    private Long restartCounter;
    private String wifiSSID;
    private String wifiPASS;
    private Date microcontrollerTime;
}
