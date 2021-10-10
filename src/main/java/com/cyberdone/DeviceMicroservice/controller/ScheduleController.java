package com.cyberdone.DeviceMicroservice.controller;

import com.cyberdone.DeviceMicroservice.model.dto.RegularScheduleDto;
import com.cyberdone.DeviceMicroservice.model.dto.RegularScheduleUpdateDto;
import com.cyberdone.DeviceMicroservice.persistence.service.RegularScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final RegularScheduleService regularScheduleService;

    @GetMapping("/{key}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<List<RegularScheduleDto>> getSchedulesByKey(@RequestParam String uuid,
                                                                      @PathVariable String key) {
        return ResponseEntity.ok(regularScheduleService.getScheduleByUuidAndMetadata(uuid, key));
    }

    @PostMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> createSchedule(@RequestBody RegularScheduleDto schedule) {
        log.info("POST {}", schedule);
        regularScheduleService.saveSchedule(schedule);
        return ResponseEntity.ok("OK");
    }

    @PatchMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateScheduleMetaInfo(@RequestBody RegularScheduleUpdateDto schedule) {
        log.info("PATCH {}", schedule);
        regularScheduleService.updateSchedule(schedule);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> deleteScheduleById(@PathVariable Long id) {
        log.info("DELETE {}", id);
        regularScheduleService.deleteSchedule(id);
        return ResponseEntity.ok("OK");
    }
}
