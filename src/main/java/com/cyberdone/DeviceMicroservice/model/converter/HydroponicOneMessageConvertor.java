package com.cyberdone.DeviceMicroservice.model.converter;

import com.cyberdone.DeviceMicroservice.model.dto.HydroponicAllDataDto;
import com.cyberdone.DeviceMicroservice.model.dto.HydroponicCustomizeDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceMetadata;
import com.cyberdone.DeviceMicroservice.persistence.entity.HydroponicData;
import com.cyberdone.DeviceMicroservice.persistence.entity.HydroponicSettings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.cyberdone.DeviceMicroservice.model.util.MqttVariableEncoderDecoderUtils.decode;


@Service
public class HydroponicOneMessageConvertor {
    public HydroponicData toDataEntity(HydroponicAllDataDto response) {
        return HydroponicData.builder()
                .receiveTime(LocalDateTime.now())
                .uuid(response.getUUID())
                .phValue(response.getPhValue())
                .temperatureValue(response.getTemperatureValue())
                .ecValue(response.getEcValue())
                .tdsValue(response.getTdsValue().intValue())
                .microcontrollerTime(decode(response.getMicrocontrollerTime()))
                .build();
    }

    public HydroponicSettings toSettingsEntity(HydroponicAllDataDto response) {
        return HydroponicSettings.builder()
                .uuid(response.getUUID())
                .mlPerMillisecond(response.getMlPerMilisecond())
                .regulateErrorPhUp(response.getRegulateErrorPhUp())
                .regulateErrorPhDown(response.getRegulateErrorPhDown())
                .regulateErrorFertilizer(response.getRegulateErrorFertilizer())
                .phUpDoseMl(response.getPhUpDoseMl())
                .phDownDoseMl(response.getPhDownDoseMl())
                .fertilizerDoseMl(response.getFertilizerDoseMl())
                .recheckDosatorsAfterMs(response.getRecheckDosatorsAfterMs())
                .setupPhValue(response.getSetupPhValue())
                .setupTdsValue(response.getSetupTdsValue())
                .setupTemperatureValue(response.getSetupTemperatureValue())
                .deviceEnable(response.getDeviceEnable() == 1)
                .dosatorsEnable(response.getDosatorsEnable() == 1)
                .sensorsEnable(response.getSensorsEnable() == 1)
                .restartCounter(response.getRestartCounter())
                .wifiSSID(response.getWifiSSID())
                .wifiPASS(response.getWifiPASS())
                .microcontrollerTime(decode(response.getMicrocontrollerTime()))
                .build();
    }

    public DeviceMetadata toCustomizationEntity(HydroponicCustomizeDto customizeDto) {
        return DeviceMetadata.builder()
                .name(customizeDto.getName())
                .description(customizeDto.getDescription())
                .build();
    }
}
