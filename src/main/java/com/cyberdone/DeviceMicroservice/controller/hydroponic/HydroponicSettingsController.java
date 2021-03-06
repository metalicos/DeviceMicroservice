package com.cyberdone.DeviceMicroservice.controller.hydroponic;

import com.cyberdone.DeviceMicroservice.model.dto.microcontrollers.hydroponic.HydroponicSettingsDto;
import com.cyberdone.DeviceMicroservice.persistence.service.HydroponicSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/hydroponic/settings")
public class HydroponicSettingsController {
    private final HydroponicSettingsService hydroponicSettingsService;

    @GetMapping("/last")
    public ResponseEntity<List<HydroponicSettingsDto>> getLastSettingsInDeviceWithUUID(
            @NotBlank(message = VALUE_IS_BLANK_MSG) @Pattern(regexp = UUID_PATTERN, message = UUID_FAILED_MSG)
            @RequestParam String uuid,
            @NotNull(message = VALUE_IS_NULL_MSG) @Positive(message = NOT_POSITIVE_MSG)
            @RequestParam Integer page,
            @NotNull(message = VALUE_IS_NULL_MSG) @Positive(message = NOT_POSITIVE_MSG)
            @RequestParam Integer limit) {
        return ResponseEntity.ok(hydroponicSettingsService.getLastSettingsByUuid(uuid, page, limit));
    }
}
