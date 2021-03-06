package com.cyberdone.DeviceMicroservice.callback;

import com.cyberdone.DeviceMicroservice.model.dto.microcontrollers.hydroponic.HydroponicAllDataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceSpecialInformation;
import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicCalibrationData;
import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicData;
import com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.HydroponicSettings;
import com.cyberdone.DeviceMicroservice.persistence.service.DeviceSpecialInformationService;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicCalibrationDataService;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicDataService;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicSettingsService;
import com.cyberdone.DeviceMicroservice.service.EncDecService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UncheckedIOException;

@Slf4j
@RequiredArgsConstructor
@Service("device-microservice/hydroponic-v1")
public class HydroponicV1SettingsCallback implements Callback {
    @Value("${security.callback.show-decrypted-message.hydroponic-v1-settings}")
    private boolean showDecryptedMessage;
    private final HydroponicDataService hydroponicDataService;
    private final HydroponicSettingsService hydroponicSettingsService;
    private final HydroponicCalibrationDataService calibrationDataService;
    private final DeviceSpecialInformationService informationService;
    private final ObjectMapper mapper;
    private final ModelMapper modelMapper;
    private final EncDecService encDecService;

    @Override
    @SneakyThrows
    @Transactional
    public void execute(MqttClient client, MqttMessage message) {
        var decryptedData = encDecService.decrypt(new String(message.getPayload()));
        if (showDecryptedMessage) {
            log.info("{}", decryptedData);
        }
        try {
            var allData = mapper.readValue(decryptedData, HydroponicAllDataDto.class);
            hydroponicDataService.saveData(modelMapper.map(allData, HydroponicData.class));
            hydroponicSettingsService.saveSetting(modelMapper.map(allData, HydroponicSettings.class));
            calibrationDataService.saveCalibrationData(modelMapper.map(allData, HydroponicCalibrationData.class));
            informationService.saveSpecialInformation(modelMapper.map(allData, DeviceSpecialInformation.class));
        } catch (JsonProcessingException e) {
            log.error("Json Read fault ", e);
            throw new UncheckedIOException(e);
        }
    }
}
