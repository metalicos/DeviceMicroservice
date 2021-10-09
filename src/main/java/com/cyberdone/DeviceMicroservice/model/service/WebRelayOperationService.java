package com.cyberdone.DeviceMicroservice.model.service;

import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class WebRelayOperationService extends AbstractCyberdoneOperationService {

    public WebRelayOperationService(MqttService mqttService) {
        super(mqttService);
    }

    public void changeRelayState(String uuid, int relayNumber, String data, ValueType type) {
        sendEncodedData(uuid, "relay/" + relayNumber, data, type);
    }
}
