package com.cyberdone.DeviceMicroservice.controller;

import com.cyberdone.DeviceMicroservice.model.dto.HydroponicSettingsDto;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hydroponic/settings")
public class SettingsController {
    private final HydroponicSettingsService hydroponicSettingsService;

    @GetMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<List<HydroponicSettingsDto>> getAllSettingsInDeviceWithUUID(@RequestParam String uuid) {
        return ResponseEntity.ok(hydroponicSettingsService.getAllSettingsByUuid(uuid));
    }

    @GetMapping("/last")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<HydroponicSettingsDto> getLastSettingInDeviceWithUUID(@RequestParam String uuid) {
        var setting = hydroponicSettingsService.getLastSettingByUuid(uuid);
        return ResponseEntity.ok(setting);
    }

    @DeleteMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<Void> deleteAllSettingsInDeviceWithUUID(@RequestParam String uuid) {
        hydroponicSettingsService.deleteAllSettingsByUuid(uuid);
        return ResponseEntity.ok().build();
    }
}
