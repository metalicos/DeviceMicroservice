package com.cyberdone.DeviceMicroservice.controller;

import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceMetadata;
import com.cyberdone.DeviceMicroservice.persistence.service.DeviceMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/metadata")
public class MetadataController {

    private final DeviceMetadataService metadataService;

    @GetMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<DeviceMetadata> getMetadataByUuid(@RequestParam String uuid) {
        return ResponseEntity.ok(metadataService.getMetadataByUuid(uuid));
    }

    @PostMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> updateMetadata(@RequestParam String uuid,
                                                 @RequestParam String name,
                                                 @RequestParam String description) {
        log.info("{} {} {}", uuid, name, description);
        metadataService.updateMetadata(uuid, name, description);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping
    @CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
    public ResponseEntity<String> deleteMetadata(@RequestParam String uuid) {
        metadataService.deleteMetadata(uuid);
        return ResponseEntity.ok("OK");
    }
}
