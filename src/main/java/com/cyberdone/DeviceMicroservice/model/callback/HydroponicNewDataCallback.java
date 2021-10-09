package com.cyberdone.DeviceMicroservice.model.callback;

import com.cyberdone.DeviceMicroservice.model.converter.HydroponicOneMessageConvertor;
import com.cyberdone.DeviceMicroservice.model.dto.HydroponicAllDataDto;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicDataService;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicSettingsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UncheckedIOException;

@Slf4j
@Service("hydro-chip")
@RequiredArgsConstructor
public class HydroponicNewDataCallback implements Callback {
    private final HydroponicDataService hydroponicDataService;
    private final HydroponicSettingsService hydroponicSettingsService;

    private final HydroponicOneMessageConvertor hydroponicOneMessageConvertor;
    private final ObjectMapper mapper;

    @Override
    @Transactional
    public void execute(MqttClient client, MqttMessage message) {
        try {
            var allData = mapper.readValue(new String(message.getPayload()), HydroponicAllDataDto.class);
            hydroponicDataService.saveData(hydroponicOneMessageConvertor.toDataEntity(allData));
            hydroponicSettingsService.saveSetting(hydroponicOneMessageConvertor.toSettingsEntity(allData));
        } catch (JsonProcessingException e) {
            log.error("Json Read fault ", e);
            throw new UncheckedIOException(e);
        }
    }
}
