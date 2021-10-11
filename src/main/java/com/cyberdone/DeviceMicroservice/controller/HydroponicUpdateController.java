package com.cyberdone.DeviceMicroservice.controller;

import com.cyberdone.DeviceMicroservice.model.dto.HydroponicTimeDto;
import com.cyberdone.DeviceMicroservice.model.service.HydroponicOneOperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.DIRECTION;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.DOUBLE;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.NUMBER;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.STRING;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.SWITCH;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/update")
public class HydroponicUpdateController {
    private final HydroponicOneOperationService operationService;

    @PostMapping("/time")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateTime(@RequestBody HydroponicTimeDto dto) {
        operationService.updateTime(dto.getUUID(), dto.getMicrocontrollerTime());
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/zone")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateZone(@RequestParam String uuid,
                                             @RequestParam String value) {
        operationService.changeTimeZoneSetting(uuid, value, STRING);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/autotime")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateTime(@RequestParam String uuid,
                                             @RequestParam String value) {
        operationService.changeAutoTimeSetting(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/pumps/phUp/{direction}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhUpPumpStatus(@RequestParam String uuid,
                                                       @PathVariable String direction) {
        log.info("Pump Ph Up; direction={} uuid={}", direction, uuid);
        switch (direction) {
            case "-1" -> operationService.startPhUpPump(uuid, "0", DIRECTION);
            case "0" -> operationService.stopPhUpPump(uuid);
            case "1" -> operationService.startPhUpPump(uuid, "1", DIRECTION);
        }
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/pumps/phDown/{direction}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhDownPumpStatus(@RequestParam String uuid,
                                                         @PathVariable String direction) {
        log.info("Pump Ph Down; direction={} uuid={}", direction, uuid);
        switch (direction) {
            case "-1" -> operationService.startPhDownPump(uuid, "0", DIRECTION);
            case "0" -> operationService.stopPhDownPump(uuid);
            case "1" -> operationService.startPhDownPump(uuid, "1", DIRECTION);
        }
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/pumps/tds/{direction}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateTdsPumpStatus(@RequestParam String uuid,
                                                      @PathVariable String direction) {
        log.info("Pump Tds; direction={} uuid={}", direction, uuid);
        switch (direction) {
            case "-1" -> operationService.startFertilizerPump(uuid, "0", DIRECTION);
            case "0" -> operationService.stopFertilizerPump(uuid);
            case "1" -> operationService.startFertilizerPump(uuid, "1", DIRECTION);
        }
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/restart")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> restart(@RequestParam String uuid) {
        log.info("Restart uuid={}", uuid);
        operationService.restart(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/save")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> saveAllSettings(@RequestParam String uuid) {
        log.info("Save Settings uuid={}", uuid);
        operationService.saveAllSettings(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/read")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> readAllSettings(@RequestParam String uuid) {
        log.info("Read Settings uuid={}", uuid);
        operationService.readAllSettings(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibrateTdsSensor(@RequestParam String uuid, @RequestParam String value) {
        log.info("Calibrate TDS; val={} uuid={}", value, uuid);
        operationService.calibrateTdsSensor(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/tds/clear")
    public ResponseEntity<String> clrCalibrationTdsSensor(@RequestParam String uuid) {
        log.info("Calibrate TDS Clear uuid={}", uuid);
        operationService.clearCalibrationOfTdsSensor(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/ph/low")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibratePhLow(@RequestParam String uuid, @RequestParam String value) {
        log.info("Calibrate Ph LOW; val={} uuid={}", value, uuid);
        operationService.calibratePhSensorLowPoint(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/ph/high")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibratePhHigh(@RequestParam String uuid, @RequestParam String value) {
        log.info("Calibrate Ph HIGH; val={} uuid={}", value, uuid);
        operationService.calibratePhSensorHighPoint(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/ph/clear")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> clrCalibrationPhSensor(@RequestParam String uuid) {
        log.info("Calibrate Ph Clear uuid={}", uuid);
        operationService.clearCalibrationOfPhSensor(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/setup/ph")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSetupPhValue(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateSetupPhValue(uuid, value, DOUBLE);
        log.info("Setup Ph; val={} uuid={}", value, uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/setup/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSetupTdsValue(@RequestParam String uuid, @RequestParam String value) {
        log.info("Setup TDS; val={} uuid={}", value, uuid);
        operationService.updateSetupTdsValue(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/recheck-time")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRecheckDosatorsAfterTime(@RequestParam String uuid,
                                                                 @RequestParam String value) {
        log.info("Recheck Time; val={} uuid={}", value, uuid);
        operationService.updateRecheckDosatorsAfterTime(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/dose/ph/up")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhUpDose(@RequestParam String uuid, @RequestParam String value) {
        log.info("Dose Ph UP; val={} uuid={}", value, uuid);
        operationService.updatePhUpDose(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/dose/ph/down")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhDownDose(@RequestParam String uuid, @RequestParam String value) {
        log.info("Dose Ph DOWN; val={} uuid={}", value, uuid);
        operationService.updatePhDownDose(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/dose/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateFertilizerDose(@RequestParam String uuid, @RequestParam String value) {
        log.info("Dose TDS; val={} uuid={}", value, uuid);
        operationService.updateFertilizerDose(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/regulator/error/ph")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRegulatePhError(@RequestParam String uuid, @RequestParam String value) {
        log.info("Reg Error Ph; val={} uuid={}", value, uuid);
        operationService.updateRegulatePhError(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/regulator/error/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRegulateTdsError(@RequestParam String uuid, @RequestParam String value) {
        log.info("Reg Error TDS; val={} uuid={}", value, uuid);
        operationService.updateRegulateTdsError(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/pump/speed")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePumpSpeed(@RequestParam String uuid, @RequestParam String value) {
        log.info("Pump speed ml/ms; val={} uuid={}", value, uuid);
        operationService.updatePumpSpeedMlPerMilliseconds(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/wifi/ssid")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateWifiSsid(@RequestParam String uuid, @RequestParam String value) {
        log.info("Wifi SSID; val={} uuid={}", value, uuid);
        operationService.updateWifiSsid(uuid, value, STRING);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/wifi/pass")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateWifiPassword(@RequestParam String uuid, @RequestParam String value) {
        log.info("Wifi PASS; val={} uuid={}", value, uuid);
        operationService.updateWifiPassword(uuid, value, STRING);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/enable/sensors")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSensorsEnable(@RequestParam String uuid, @RequestParam String value) {
        log.info("Enable sensors; val={} uuid={}", value, uuid);
        operationService.updateSensorsEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/enable/dosators")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateDosatorsEnable(@RequestParam String uuid, @RequestParam String value) {
        log.info("Enable dosators; val={} uuid={}", value, uuid);
        operationService.updateDosatorsEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/enable/device")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateDeviceEnable(@RequestParam String uuid, @RequestParam String value) {
        log.info("Enable device; val={} uuid={}", value, uuid);
        operationService.updateDeviceEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }
}
