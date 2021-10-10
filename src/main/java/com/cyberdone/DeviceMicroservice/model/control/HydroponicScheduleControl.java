package com.cyberdone.DeviceMicroservice.model.control;

import com.cyberdone.DeviceMicroservice.model.service.HydroponicOneOperationService;
import com.cyberdone.DeviceMicroservice.persistence.entity.ValueType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("hydroponic")
@RequiredArgsConstructor
public class HydroponicScheduleControl implements ScheduleControllable {
    private final HydroponicOneOperationService operationService;

    @Override
    public void control(ValueType type, String data, String topic, String uuid) {
        switch (topic) {
            case "phUpStart" -> operationService.startPhUpPump(uuid, data, type);
            case "phUpStop" -> operationService.stopPhUpPump(uuid);
            case "phDownStart" -> operationService.startPhDownPump(uuid, data, type);
            case "phDownStop" -> operationService.stopPhDownPump(uuid);
            case "fertilizerStart" -> operationService.startFertilizerPump(uuid, data, type);
            case "fertilizerStop" -> operationService.stopFertilizerPump(uuid);
            case "calibrateTds" -> operationService.calibrateTdsSensor(uuid, data, type);
            case "calibrateTdsClear" -> operationService.clearCalibrationOfTdsSensor(uuid);
            case "calibratePhLow" -> operationService.calibratePhSensorLowPoint(uuid, data, type);
            case "calibratePhHigh" -> operationService.calibratePhSensorHighPoint(uuid, data, type);
            case "calibratePhClear" -> operationService.clearCalibrationOfPhSensor(uuid);
            case "setupPhValue" -> operationService.updateSetupPhValue(uuid, data, type);
            case "setupTdsValue" -> operationService.updateSetupTdsValue(uuid, data, type);
            case "recheckDosatorsAfterMs" -> operationService.updateRecheckDosatorsAfterTime(uuid, data, type);
            case "phUpDoseMl" -> operationService.updatePhUpDose(uuid, data, type);
            case "phDownDoseMl" -> operationService.updatePhDownDose(uuid, data, type);
            case "fertilizerDoseMl" -> operationService.updateFertilizerDose(uuid, data, type);
            case "regulateErrorPh" -> operationService.updateRegulatePhError(uuid, data, type);
            case "regulateErrorFertilizer" -> operationService.updateRegulateTdsError(uuid, data, type);
            case "mlPerMilisecond" -> operationService.updatePumpSpeedMlPerMilliseconds(uuid, data, type);
            case "dosatorsEnable" -> operationService.updateDosatorsEnable(uuid, data, type);
        }
        controlCommonTopics(operationService, type, data, topic, uuid);
    }

}
