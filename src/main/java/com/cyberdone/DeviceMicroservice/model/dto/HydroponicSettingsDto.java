package com.cyberdone.DeviceMicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Long recheckDispensersAfterMs;
    private Double setupPhValue;
    private Long setupTdsValue;
    private Double setupTemperatureValue;
    private Boolean deviceEnable;
    private Boolean DispensersEnable;
    private Boolean sensorsEnable;
    private Long restartCounter;
    private String wifiSSID;
    private String wifiPASS;
    private Boolean isDispenserPhUpOpen;
    private Boolean isDispenserPhDownOpen;
    private Boolean isDispenserTdsOpen;
    private Boolean autotime;
    private String timeZone;
    private LocalDateTime microcontrollerTime;
}
