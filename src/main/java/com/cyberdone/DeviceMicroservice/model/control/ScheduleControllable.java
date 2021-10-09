package com.cyberdone.DeviceMicroservice.model.control;

import com.cyberdone.DeviceMicroservice.model.service.HydroponicOneOperationService;
import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;

public interface ScheduleControllable {
    void control(ValueType type, String data, String topic, String uuid);

    default void controlCommonTopics(HydroponicOneOperationService operationService, ValueType type,
                                     String data, String topic, String uuid) {
        switch (topic) {
            case "restart" -> operationService.restart(uuid);
            case "saveAll" -> operationService.saveAllSettings(uuid);
            case "readAll" -> operationService.readAllSettings(uuid);
            case "wifiSSID" -> operationService.updateWifiSsid(uuid, data, type);
            case "wifiPASS" -> operationService.updateWifiPassword(uuid, data, type);
            case "deviceEnable" -> operationService.updateDeviceEnable(uuid, data, type);
            case "sensorsEnable" -> operationService.updateSensorsEnable(uuid, data, type);
        }
    }
}
