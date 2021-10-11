package com.cyberdone.DeviceMicroservice.controller;

import com.cyberdone.DeviceMicroservice.model.dto.HydroponicDataDto;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hydroponic/data")
public class DataController {
    private final HydroponicDataService hydroponicDataService;

    @GetMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<List<HydroponicDataDto>> getAllDataInDeviceWithUUID(@RequestParam String uuid) {
        return ResponseEntity.ok(hydroponicDataService.getAllDataByUuid(uuid));
    }

    @GetMapping("/last")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<HydroponicDataDto> getLastDataInDeviceWithUUID(@RequestParam String uuid) {
        return ResponseEntity.ok(hydroponicDataService.getLastDataByUuid(uuid));
    }

    @GetMapping("/last/{amount}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<List<HydroponicDataDto>> getLastLimitedDataByUuid(@RequestParam String uuid,
                                                                            @PathVariable Integer amount) {
        return ResponseEntity.ok(hydroponicDataService.getLastLimitedDataByUuid(uuid, amount));
    }

    @DeleteMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<Void> deleteAllDataInDeviceWithUUID(@RequestParam String uuid) {
        hydroponicDataService.deleteDataByUuid(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/avr")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<HydroponicDataDto> getAverageData(@RequestParam String uuid) {
        return ResponseEntity.ok(hydroponicDataService.getAverageDataByUuid(uuid));
    }
}
