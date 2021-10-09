package com.cyberdone.DeviceMicroservice.model.service;

import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import static com.cyberdone.DeviceMicroservice.model.util.MqttVariableEncoderDecoderUtils.encode;
import static com.cyberdone.DeviceMicroservice.model.util.MqttVariableEncoderDecoderUtils.encodeConsideringToValueType;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.NONE;

@Slf4j
public abstract class AbstractCyberdoneOperationService {
    protected static final String SLASH = "/";
    private final MqttService mqttService;

    protected AbstractCyberdoneOperationService(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    public void sendEncodedData(String uuid, String topic, String data, ValueType valueType) {
        sendData(uuid, topic, encodeConsideringToValueType(data, valueType));
    }

    public void sendData(String uuid, String topic, String data) {
        Timer timer = new Timer();
        int delayMs = 0;
        int periodMs = 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            int retryCounter = 2;

            @Override
            public void run() {
                try {
                    var path = "cyberdone" + SLASH + uuid + SLASH + topic;
                    mqttService.sendData(path, data.getBytes(StandardCharsets.UTF_8), 2, true);
                    timer.cancel();
                } catch (MqttException ex) {
                    log.error("Fail to send data to device uuid={} topic={} data={}", uuid, topic, data);
                    if (retryCounter <= 0) {
                        timer.cancel();
                    }
                    retryCounter -= 1;
                }
            }
        }, delayMs, periodMs);
    }

    public void updateTime(String uuid, String time, ValueType type) {
        sendEncodedData(uuid, "updateTime", time, type);
    }

    public void updateTime(String uuid, LocalDateTime time) {
        sendData(uuid, "updateTime", encode(time));
    }

    public void restart(String uuid) {
        sendEncodedData(uuid, "restart", null, NONE);
    }

    public void saveAllSettings(String uuid) {
        sendEncodedData(uuid, "saveAll", null, NONE);
    }

    public void readAllSettings(String uuid) {
        sendEncodedData(uuid, "readAll", null, NONE);
    }

    public void updateWifiPassword(String uuid, String password, ValueType type) {
        sendEncodedData(uuid, "wifiPASS", password, type);
    }

    public void updateWifiSsid(String uuid, String ssid, ValueType type) {
        sendEncodedData(uuid, "wifiSSID", ssid, type);
    }
}
