package com.cyberdone.DeviceMicroservice.persistence.entity.relay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RELAY_SETTINGS")
public class RelaySettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean scheduledRelays;
    private Integer relay1;
    private Integer relay2;
    private Integer relay3;
    private Integer relay4;

    private String wifiSSID;
    private String wifiPASS;
    private LocalDateTime microcontrollerTime;

    private String timeZone;
    private Boolean autotime;
    private Long restartCounter;
    private String uuid;
}
