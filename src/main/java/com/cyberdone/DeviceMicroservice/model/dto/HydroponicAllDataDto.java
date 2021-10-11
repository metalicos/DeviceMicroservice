package com.cyberdone.DeviceMicroservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HydroponicAllDataDto {
    private Double phValue;
    private Double temperatureValue;
    private Double ecValue;
    private Long tdsValue;
    private Double mlPerMilisecond;
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
    private Long deviceEnable;
    private Long dosatorsEnable;
    private Long sensorsEnable;
    private Long restartCounter;
    private String wifiSSID;
    private String wifiPASS;
    private Boolean isDosatorPhUpOpen;
    private Boolean isDosatorPhDownOpen;
    private Boolean isDosatorTdsOpen;

    private Boolean autotime;
    private String timeZone;
    private LocalDateTime microcontrollerTime;

    @JsonProperty("UUID")
    private String UUID;
}
