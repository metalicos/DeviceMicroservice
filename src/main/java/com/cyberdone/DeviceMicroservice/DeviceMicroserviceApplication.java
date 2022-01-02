package com.cyberdone.DeviceMicroservice;

import com.cyberdone.DeviceMicroservice.service.MqttService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class DeviceMicroserviceApplication implements CommandLineRunner {
    public final MqttService mqttService;

    public static void main(String[] args) {
        SpringApplication.run(DeviceMicroserviceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        mqttService.start();
    }
}
