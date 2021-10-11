package com.cyberdone.DeviceMicroservice.model.callback;

import com.cyberdone.DeviceMicroservice.model.dto.HydroponicTimeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.cyberdone.DeviceMicroservice.model.util.MqttVariableEncoderDecoderUtils.encode;


@Slf4j
@Service("time")
@RequiredArgsConstructor
public class UpdateTimeCallback implements Callback {
    public static final int DATE_MISMATCH_ERROR_SECONDS = 5;
    private final ObjectMapper objectMapper;

    @Override
    public void execute(MqttClient client, MqttMessage message) {
        try {
            keepInTime(client, objectMapper.readValue(new String(message.getPayload()), HydroponicTimeDto.class));
        } catch (JsonProcessingException | ParseException ex) {
            log.error("Error read Microcontroller's Date. {}", ex.getMessage());
        }
    }

    private void keepInTime(MqttClient client, HydroponicTimeDto hydroponicTimeDto) throws ParseException {
        var hydroponicTime = hydroponicTimeDto.getMicrocontrollerTime();
        var timeZone = hydroponicTimeDto.getMicrocontrollerTimeZone();
        var currentTimeForItsTimezone = LocalDateTime.now(ZoneId.of(timeZone));
        var lowRange = currentTimeForItsTimezone.minusSeconds(DATE_MISMATCH_ERROR_SECONDS);
        var highRange = currentTimeForItsTimezone.plusSeconds(DATE_MISMATCH_ERROR_SECONDS);

        if (hydroponicTime.isBefore(lowRange) || hydroponicTime.isAfter(highRange)) {
            try {
                client.publish("cyberdone/" + hydroponicTimeDto.getUUID() + "/updateTime",
                        new MqttMessage(encode(currentTimeForItsTimezone).getBytes(StandardCharsets.UTF_8)));
                log.info("Time updated. Device UUID={}", hydroponicTimeDto.getUUID());
            } catch (MqttException e) {
                log.error("Can not update time in microcontroller. Device UUID={} Message={}",
                        hydroponicTimeDto.getUUID(), e.getMessage());
            }
        }
    }
}
