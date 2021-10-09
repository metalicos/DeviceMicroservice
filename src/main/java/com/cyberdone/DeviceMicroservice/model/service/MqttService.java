package com.cyberdone.DeviceMicroservice.model.service;

import com.cyberdone.DeviceMicroservice.model.callback.Callback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService implements MqttCallback {
    private final Map<String, Callback> callbacks;
    private final MqttSetupService setupService;
    private MqttClient client = null;

    @Override
    public void connectionLost(Throwable cause) {
        restartIfNotConnected();
        log.error("Connection is lost. Time:{} Cause:{}", LocalDateTime.now(), cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        callbacks.get(Optional.ofNullable(topic).orElseThrow(
                        () -> new IllegalStateException("Argument: topic is not valid")))
                .execute(client, Optional.ofNullable(message).orElseThrow(
                        () -> new IllegalStateException("Argument: MqttMessage is not valid")));
        log.debug("{}", new Date());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public void start() {
        try {
            client = setupService.createMqttClient();
            client.setCallback(this);
            client.connect(setupService.createConnectionOptions());
            client.subscribe("hydro-chip", 2);
            client.subscribe("time", 2);
        } catch (MqttException e) {
            log.error("Mqtt connect failed {}", e.getMessage());
        }
    }

    public void sendData(String topic, byte[] data, int qos, boolean retained) throws MqttException {
        client.publish(topic, data, qos, retained);
    }

    public void stop() {
        try {
            if (nonNull(client)) {
                client.disconnectForcibly();
            }
        } catch (MqttException e) {
            log.error("Mqtt disconnect failed {}", e.getMessage());
        }
    }

    public void restartIfNotConnected() {
        try {
            if (isNull(client) || !client.isConnected()) {
                stop();
                start();
            }
        } catch (Exception ex) {
            log.error("Reconnect failed. {}", ex.getMessage());
        }
    }

    public void run() {
        start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                restartIfNotConnected();
            }
        }, 1_000, 1_000);
    }
}
