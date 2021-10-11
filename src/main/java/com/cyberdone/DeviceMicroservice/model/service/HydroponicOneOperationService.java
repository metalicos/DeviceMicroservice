package com.cyberdone.DeviceMicroservice.model.service;


import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.NONE;

@Slf4j
@Service
public class HydroponicOneOperationService extends AbstractCyberdoneOperationService {

    public HydroponicOneOperationService(MqttService mqttService) {
        super(mqttService);
    }

    public void startPhUpPump(String uuid, String direction, ValueType type) {
        sendEncodedData(uuid, "phUpStart", direction, type);
    }

    public void stopPhUpPump(String uuid) {
        sendEncodedData(uuid, "phUpStop", null, NONE);
    }

    public void startPhDownPump(String uuid, String direction, ValueType type) {
        sendEncodedData(uuid, "phDownStart", direction, type);
    }

    public void stopPhDownPump(String uuid) {
        sendEncodedData(uuid, "phDownStop", null, NONE);
    }

    public void startFertilizerPump(String uuid, String direction, ValueType type) {
        sendEncodedData(uuid, "fertilizerStart", direction, type);
    }

    public void stopFertilizerPump(String uuid) {
        sendEncodedData(uuid, "fertilizerStop", null, NONE);
    }

    public void calibrateTdsSensor(String uuid, String etaloneValue, ValueType type) {
        sendEncodedData(uuid, "calibrateTds", etaloneValue, type);
    }

    public void clearCalibrationOfTdsSensor(String uuid) {
        sendEncodedData(uuid, "calibrateTdsClear", null, NONE);
    }

    public void calibratePhSensorLowPoint(String uuid, String etaloneValue, ValueType type) {
        sendEncodedData(uuid, "calibratePhLow", etaloneValue, type);
    }

    public void calibratePhSensorHighPoint(String uuid, String etaloneValue, ValueType type) {
        sendEncodedData(uuid, "calibratePhHigh", etaloneValue, type);
    }

    public void clearCalibrationOfPhSensor(String uuid) {
        sendEncodedData(uuid, "calibratePhClear", null, NONE);
    }

    public void updateSetupPhValue(String uuid, String phValue, ValueType type) {
        sendEncodedData(uuid, "setupPhValue", phValue, type);
    }

    public void updateSetupTdsValue(String uuid, String tdsValue, ValueType type) {
        sendEncodedData(uuid, "setupTdsValue", tdsValue, type);
    }

    public void updateRecheckDosatorsAfterTime(String uuid, String timeInMs, ValueType type) {
        sendEncodedData(uuid, "recheckDosatorsAfterMs", timeInMs, type);
    }

    public void updatePhUpDose(String uuid, String doseMl, ValueType type) {
        sendEncodedData(uuid, "phUpDoseMl", doseMl, type);
    }

    public void updatePhDownDose(String uuid, String doseMl, ValueType type) {
        sendEncodedData(uuid, "phDownDoseMl", doseMl, type);
    }

    public void updateFertilizerDose(String uuid, String doseMl, ValueType type) {
        sendEncodedData(uuid, "fertilizerDoseMl", doseMl, type);
    }

    public void updateRegulatePhError(String uuid, String phError, ValueType type) {
        sendEncodedData(uuid, "regulateErrorPhUp", phError, type);
        sendEncodedData(uuid, "regulateErrorPhDown", phError, type);
    }

    public void updateRegulateTdsError(String uuid, String ppmError, ValueType type) {
        sendEncodedData(uuid, "regulateErrorFertilizer", ppmError, type);
    }

    public void updatePumpSpeedMlPerMilliseconds(String uuid, String mlPerMillisecond, ValueType type) {
        sendEncodedData(uuid, "mlPerMilisecond", mlPerMillisecond, type);
    }

    public void updateSensorsEnable(String uuid, String makeEnable, ValueType type) {
        sendEncodedData(uuid, "sensorsEnable", makeEnable, type);
    }

    public void updateDosatorsEnable(String uuid, String value, ValueType type) {
        sendEncodedData(uuid, "dosatorsEnable", value, type);
    }

    public void updateDeviceEnable(String uuid, String value, ValueType type) {
        sendEncodedData(uuid, "deviceEnable", value, type);
    }

    public void changeAutoTimeSetting(String uuid, String value, ValueType type) {
        sendEncodedData(uuid, "autotime", value, type);
    }

    public void changeTimeZoneSetting(String uuid, String value, ValueType type) {
        sendEncodedData(uuid, "timezone", value, type);
    }
}
