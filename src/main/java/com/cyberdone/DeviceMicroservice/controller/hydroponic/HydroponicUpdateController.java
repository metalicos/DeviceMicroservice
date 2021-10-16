package com.cyberdone.DeviceMicroservice.controller.hydroponic;

import com.cyberdone.DeviceMicroservice.model.dto.microcontrollers.HydroponicTimeDto;
import com.cyberdone.DeviceMicroservice.model.service.HydroponicOneOperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.DIRECTION;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.NUMBER;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.SWITCH;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.TEXT;
import static com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.DirectionEnum.LEFT;
import static com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.DirectionEnum.RIGHT;
import static com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic.DirectionEnum.STOP;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.DIRECTION_FAILED_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.DIRECTION_PATTERN;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.NUMBER_FAILED_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.NUMBER_PATTERN;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.SWITCH_FAILED_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.SWITCH_PATTERN;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.TEXT_FAILED_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.TEXT_PATTERN;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.UUID_FAILED_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.UUID_PATTERN;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_BLANK_MSG;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/update")
public class HydroponicUpdateController {
    private final HydroponicOneOperationService operationService;

    @PostMapping("/time")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateTime(@Valid @RequestBody HydroponicTimeDto dto) {
        operationService.updateTime(dto.getUuid(), dto.getMicrocontrollerTime());
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/zone")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateZone(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG)
            @RequestParam String value) {
        operationService.changeTimeZoneSetting(uuid, value, TEXT);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/autotime")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateTime(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        operationService.changeAutoTimeSetting(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/pumps/phUp/{direction}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhUpPumpStatus(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = DIRECTION_PATTERN, message = DIRECTION_FAILED_MSG)
            @PathVariable String direction) {
        log.info("Pump Ph Up; direction={} uuid={}", direction, uuid);
        switch (direction) {
            case "-1" -> operationService.phUpPump(uuid, LEFT, DIRECTION);
            case "0" -> operationService.phUpPump(uuid, STOP, DIRECTION);
            case "1" -> operationService.phUpPump(uuid, RIGHT, DIRECTION);
        }
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/pumps/phDown/{direction}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhDownPumpStatus(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = DIRECTION_PATTERN, message = DIRECTION_FAILED_MSG)
            @PathVariable String direction) {
        log.info("Pump Ph Down; direction={} uuid={}", direction, uuid);
        switch (direction) {
            case "-1" -> operationService.phDownPump(uuid, LEFT, DIRECTION);
            case "0" -> operationService.phDownPump(uuid, STOP, DIRECTION);
            case "1" -> operationService.phDownPump(uuid, RIGHT, DIRECTION);
        }
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/pumps/tds/{direction}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateTdsPumpStatus(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = DIRECTION_PATTERN, message = DIRECTION_FAILED_MSG)
            @PathVariable String direction) {
        log.info("Pump Tds; direction={} uuid={}", direction, uuid);
        switch (direction) {
            case "-1" -> operationService.tdsPump(uuid, LEFT, DIRECTION);
            case "0" -> operationService.tdsPump(uuid, STOP, DIRECTION);
            case "1" -> operationService.tdsPump(uuid, RIGHT, DIRECTION);
        }
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/restart")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> restart(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        log.info("Restart uuid={}", uuid);
        operationService.restart(uuid);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/save")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> saveAllSettings(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        log.info("Save Settings uuid={}", uuid);
        operationService.saveAllSettings(uuid);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/read")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> readAllSettings(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        log.info("Read Settings uuid={}", uuid);
        operationService.readAllSettings(uuid);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/calibrate/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibrateTdsSensor(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Calibrate TDS; val={} uuid={}", value, uuid);
        operationService.calibrateTdsSensor(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/calibrate/tds/clear")
    public ResponseEntity<String> clrCalibrationTdsSensor(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        log.info("Calibrate TDS Clear uuid={}", uuid);
        operationService.clearCalibrationOfTdsSensor(uuid);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/calibrate/ph/low")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibratePhLow(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Calibrate Ph LOW; val={} uuid={}", value, uuid);
        operationService.calibratePhSensorLowPoint(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/calibrate/ph/high")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibratePhHigh(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Calibrate Ph HIGH; val={} uuid={}", value, uuid);
        operationService.calibratePhSensorHighPoint(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/calibrate/ph/clear")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> clrCalibrationPhSensor(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        log.info("Calibrate Ph Clear uuid={}", uuid);
        operationService.clearCalibrationOfPhSensor(uuid);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/setup/ph")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSetupPhValue(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        operationService.updateSetupPhValue(uuid, value, NUMBER);
        log.info("Setup Ph; val={} uuid={}", value, uuid);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/setup/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSetupTdsValue(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Setup TDS; val={} uuid={}", value, uuid);
        operationService.updateSetupTdsValue(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/dispensers/recheck-time")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRecheckDispensersAfterTime(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Recheck Time; val={} uuid={}", value, uuid);
        operationService.updateRecheckDispensersAfterTime(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/dose/ph/up")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhUpDose(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Dose Ph UP; val={} uuid={}", value, uuid);
        operationService.updatePhUpDose(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/dose/ph/down")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhDownDose(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Dose Ph DOWN; val={} uuid={}", value, uuid);
        operationService.updatePhDownDose(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/dose/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateFertilizerDose(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Dose TDS; val={} uuid={}", value, uuid);
        operationService.updateTdsDose(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/regulator/error/ph")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRegulatePhError(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Reg Error Ph; val={} uuid={}", value, uuid);
        operationService.updateRegulatePhError(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/regulator/error/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRegulateTdsError(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Reg Error TDS; val={} uuid={}", value, uuid);
        operationService.updateRegulateTdsError(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/pump/speed")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePumpSpeed(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = NUMBER_PATTERN, message = NUMBER_FAILED_MSG)
            @RequestParam String value) {
        log.info("Pump speed ml/ms; val={} uuid={}", value, uuid);
        operationService.updatePumpSpeedMlPerMilliseconds(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/wifi/ssid")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateWifiSsid(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = TEXT_PATTERN, message = TEXT_FAILED_MSG)
            @RequestParam String value) {
        log.info("Wifi SSID; val={} uuid={}", value, uuid);
        operationService.updateWifiSsid(uuid, value, TEXT);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/wifi/pass")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateWifiPassword(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = TEXT_PATTERN, message = TEXT_FAILED_MSG)
            @RequestParam String value) {
        log.info("Wifi PASS; val={} uuid={}", value, uuid);
        operationService.updateWifiPassword(uuid, value, TEXT);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/enable/sensors")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSensorsEnable(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = SWITCH_PATTERN, message = SWITCH_FAILED_MSG)
            @RequestParam String value) {
        log.info("Enable sensors; val={} uuid={}", value, uuid);
        operationService.updateSensorsEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/enable/dispensers")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateDispensersEnable(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = SWITCH_PATTERN, message = SWITCH_FAILED_MSG)
            @RequestParam String value) {
        log.info("Enable Dispensers; val={} uuid={}", value, uuid);
        operationService.updateDispensersEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }
}
