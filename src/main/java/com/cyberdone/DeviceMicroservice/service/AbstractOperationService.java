package com.cyberdone.DeviceMicroservice.service;

import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.NONE;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.TIME;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.AUTOTIME;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.READ_ALL;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.RESTART;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.RESTART_COUNTER;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.SAVE_ALL;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.TIMEZONE;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.UPDATE_TIME;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.WIFI_PASS;
import static com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum.WIFI_SSID;
import static com.cyberdone.DeviceMicroservice.util.MqttVariableEncoderDecoderUtils.encode;
import static com.cyberdone.DeviceMicroservice.util.MqttVariableEncoderDecoderUtils.encodeConsideringToValueType;

@Slf4j
public abstract class AbstractOperationService {
    protected static final String SLASH = "/";
    private final MqttService mqttService;

    protected AbstractOperationService(MqttService mqttService) {
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

    public void updateTime(String uuid, LocalDateTime time) {
        sendData(uuid, UPDATE_TIME.getVal(), encode(time));
    }

    public void updateTime(String uuid, String time) {
        sendEncodedData(uuid, UPDATE_TIME.getVal(), time, TIME);
    }

    public void changeAutoTimeSetting(String uuid, String value, ValueType type) {
        sendEncodedData(uuid, AUTOTIME.getVal(), value, type);
    }

    public void changeTimeZoneSetting(String uuid, String value, ValueType type) {
        sendEncodedData(uuid, TIMEZONE.getVal(), value, type);
    }

    public void restart(String uuid) {
        sendEncodedData(uuid, RESTART.getVal(), null, NONE);
    }

    public void restartCounter(String uuid, String value, ValueType type) {
        sendEncodedData(uuid, RESTART_COUNTER.getVal(), value, type);
    }

    public void saveAllSettings(String uuid) {
        sendEncodedData(uuid, SAVE_ALL.getVal(), null, NONE);
    }

    public void readAllSettings(String uuid) {
        sendEncodedData(uuid, READ_ALL.getVal(), null, NONE);
    }

    public void updateWifiPassword(String uuid, String password, ValueType type) {
        sendEncodedData(uuid, WIFI_PASS.getVal(), password, type);
    }

    public void updateWifiSsid(String uuid, String ssid, ValueType type) {
        sendEncodedData(uuid, WIFI_SSID.getVal(), ssid, type);
    }
}
