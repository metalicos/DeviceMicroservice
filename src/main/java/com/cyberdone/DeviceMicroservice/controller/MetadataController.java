package com.cyberdone.DeviceMicroservice.controller;

import com.cyberdone.DeviceMicroservice.model.dto.DeviceMetadataDto;
import com.cyberdone.DeviceMicroservice.persistence.entity.DeviceType;
import com.cyberdone.DeviceMicroservice.persistence.service.DeviceMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.NOT_POSITIVE_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.UUID_FAILED_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.UUID_PATTERN;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_BLANK_MSG;
import static com.cyberdone.DeviceMicroservice.validation.ValidationConstants.VALUE_IS_NULL_MSG;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/metadata")
public class MetadataController {
    private final DeviceMetadataService metadataService;

    @GetMapping
    public ResponseEntity<DeviceMetadataDto> getMetadataByUuid(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        return ResponseEntity.ok(metadataService.getMetadataByUuid(uuid));
    }

    @GetMapping("/list")
    public ResponseEntity<List<DeviceMetadataDto>> getMetadataByUser(
            @NotNull(message = VALUE_IS_NULL_MSG) @Positive(message = NOT_POSITIVE_MSG)
            @RequestParam Long userId) {
        return ResponseEntity.ok(metadataService.getMetadataByUser(userId));
    }

    @PatchMapping
    public ResponseEntity<String> updateMetadata(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotBlank(message = VALUE_IS_BLANK_MSG)
            @RequestParam String name,
            @NotBlank(message = VALUE_IS_BLANK_MSG)
            @RequestParam String description) {
        log.info("{} {} {}", uuid, name, description);
        metadataService.updateMetadata(uuid, name, description);
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity<String> createMetadata(@RequestBody @Valid DeviceMetadataDto metadataDto) {
        log.info("Creating Device with Metadata: {}", metadataDto);
        metadataService.saveMetadata(metadataDto);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMetadata(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        metadataService.deleteMetadata(uuid);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/device-types")
    public ResponseEntity<DeviceType[]> getDeviceTypesList() {
        return ResponseEntity.ok(DeviceType.values());
    }

    @PutMapping("/unlink")
    public ResponseEntity<String> getDeviceTypesList(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid) {
        metadataService.unlinkMetadataFromUser(uuid);
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/link")
    public ResponseEntity<String> getDeviceTypesList(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotNull(message = VALUE_IS_NULL_MSG) @Positive(message = NOT_POSITIVE_MSG)
            @RequestParam Long userId) {
        metadataService.linkMetadataToUser(uuid, userId);
        return ResponseEntity.ok("OK");
    }
}
