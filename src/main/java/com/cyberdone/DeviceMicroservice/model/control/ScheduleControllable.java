package com.cyberdone.DeviceMicroservice.model.control;

import com.cyberdone.DeviceMicroservice.model.service.AbstractOperationService;
import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import com.cyberdone.DeviceMicroservice.persistence.entity.topic.CommonTopicEnum;
import org.slf4j.Logger;

public interface ScheduleControllable {
    void control(ValueType type, String data, String topic, String uuid);

    default void controlCommonTopics(AbstractOperationService operationService, ValueType type,
                                     String data, String topic, String uuid, Logger log) {
        switch (CommonTopicEnum.topic(topic)) {
            case UPDATE_TIME -> operationService.updateTime(uuid, data);
            case TIMEZONE -> operationService.changeTimeZoneSetting(uuid, data, type);
            case AUTOTIME -> operationService.changeAutoTimeSetting(uuid, data, type);
            case RESTART -> operationService.restart(uuid);
            case RESTART_COUNTER -> operationService.restartCounter(uuid, data, type);
            case READ_ALL -> operationService.readAllSettings(uuid);
            case SAVE_ALL -> operationService.saveAllSettings(uuid);
            case WIFI_SSID -> operationService.updateWifiSsid(uuid, data, type);
            case WIFI_PASS -> operationService.updateWifiPassword(uuid, data, type);
            case UNKNOWN -> log.info("Topic={} is not of type HydroponicTopic", topic);
        }
    }
}
