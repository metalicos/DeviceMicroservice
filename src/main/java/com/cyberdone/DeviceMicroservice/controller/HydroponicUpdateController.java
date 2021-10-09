package com.cyberdone.DeviceMicroservice.controller;

import com.cyberdone.DeviceMicroservice.model.service.HydroponicOneOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.DIRECTION;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.DOUBLE;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.NUMBER;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.STRING;
import static com.cyberdone.DeviceMicroservice.persistence.entity.ValueType.SWITCH;


@RestController
@RequiredArgsConstructor
@RequestMapping("/update")
public class HydroponicUpdateController {
    private final HydroponicOneOperationService operationService;

    @GetMapping("/time")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateTime(@RequestParam String uuid,
                                             @RequestParam Integer year,
                                             @RequestParam Integer month,
                                             @RequestParam Integer day,
                                             @RequestParam Integer hour,
                                             @RequestParam Integer minute,
                                             @RequestParam Integer second) {
        operationService.updateTime(uuid, LocalDateTime.of(year, month, day, hour, minute, second));
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/pumps/phUp/{direction}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhUpPumpStatus(@RequestParam String uuid,
                                                       @PathVariable String direction) {
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
        operationService.restart(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/save")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> saveAllSettings(@RequestParam String uuid) {
        operationService.saveAllSettings(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/read")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> readAllSettings(@RequestParam String uuid) {
        operationService.readAllSettings(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibrateTdsSensor(@RequestParam String uuid, @RequestParam String value) {
        operationService.calibrateTdsSensor(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/tds/clear")
    public ResponseEntity<String> clrCalibrationTdsSensor(@RequestParam String uuid) {
        operationService.clearCalibrationOfTdsSensor(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/ph/low")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibratePhLow(@RequestParam String uuid, @RequestParam String value) {
        operationService.calibratePhSensorLowPoint(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/ph/high")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> calibratePhHigh(@RequestParam String uuid, @RequestParam String value) {
        operationService.calibratePhSensorHighPoint(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/calibrate/ph/clear")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> clrCalibrationPhSensor(@RequestParam String uuid) {
        operationService.clearCalibrationOfPhSensor(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/setup/ph")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSetupPhValue(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateSetupPhValue(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/setup/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSetupTdsValue(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateSetupTdsValue(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/recheck-time")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRecheckDosatorsAfterTime(@RequestParam String uuid,
                                                                 @RequestParam String value) {
        operationService.updateRecheckDosatorsAfterTime(uuid, value, NUMBER);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/dose/ph/up")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhUpDose(@RequestParam String uuid, @RequestParam String value) {
        operationService.updatePhUpDose(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/dose/ph/down")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePhDownDose(@RequestParam String uuid, @RequestParam String value) {
        operationService.updatePhDownDose(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/dose/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateFertilizerDose(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateFertilizerDose(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/regulator/error/ph")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRegulatePhError(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateRegulatePhError(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/regulator/error/tds")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateRegulateTdsError(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateRegulateTdsError(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/pump/speed")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updatePumpSpeed(@RequestParam String uuid, @RequestParam String value) {
        operationService.updatePumpSpeedMlPerMilliseconds(uuid, value, DOUBLE);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/wifi/ssid")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateWifiSsid(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateWifiSsid(uuid, value, STRING);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/wifi/pass")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateWifiPassword(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateWifiPassword(uuid, value, STRING);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/enable/sensors")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateSensorsEnable(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateSensorsEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/enable/dosators")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateDosatorsEnable(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateDosatorsEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/enable/device")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateDeviceEnable(@RequestParam String uuid, @RequestParam String value) {
        operationService.updateDeviceEnable(uuid, value, SWITCH);
        return ResponseEntity.ok("OK");
    }
}
