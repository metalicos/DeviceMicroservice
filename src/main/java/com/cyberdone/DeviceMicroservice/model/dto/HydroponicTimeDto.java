package com.cyberdone.DeviceMicroservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HydroponicTimeDto {
    /**
     * Time in microcontroller.
     */
    private String microcontrollerTime;
    /**
     * Device UUID.
     */
    @JsonProperty("UUID")
    private String UUID;
}
