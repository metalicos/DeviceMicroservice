package com.cyberdone.DeviceMicroservice.service;

import com.cyberdone.DeviceMicroservice.callback.Callback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        // not needed for implementation
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        callbacks.get(topic).execute(client, Optional.ofNullable(message)
                .orElseThrow(() -> new IllegalStateException("Argument: MqttMessage is not valid")));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public void start() {
        try {
            client = setupService.createMqttClient();
            client.setCallback(this);
            client.connect(setupService.createConnectionOptions());
            for (String topic : callbacks.keySet()) {
                client.subscribe(topic, 2);
            }
            log.error("Connection started... Time:{}", LocalDateTime.now());
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
                log.error("Connection stopped... Time:{}", LocalDateTime.now());
            }
        } catch (MqttException e) {
            log.error("Mqtt disconnect failed {}", e.getMessage());
        }
    }

    public void restartIfNotConnected() {
        try {
            if (isNull(client) || !client.isConnected()) {
                log.error("Connection is lost. Reconnecting... Time:{}", LocalDateTime.now());
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
        }, 0, 5_000);
    }
}
