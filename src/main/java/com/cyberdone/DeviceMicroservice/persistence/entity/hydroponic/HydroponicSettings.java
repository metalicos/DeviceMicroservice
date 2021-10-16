package com.cyberdone.DeviceMicroservice.persistence.entity.hydroponic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HYDROPONIC_SETTINGS")
public class HydroponicSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime receiveTime;
    private String uuid;

    private Double phValue;
    private Double temperatureValue;
    private Double tdsValue;
    private Boolean isDispenserPhUpOpen;
    private Boolean isDispenserPhDownOpen;
    private Boolean isDispenserTdsOpen;
    private Double setupPhValue;
    private Long setupTdsValue;
    private Double regulateErrorPh;
    private Double regulateErrorFertilizer;
    private Double mlPerMilisecond;
    private Double phUpDoseMl;
    private Double phDownDoseMl;
    private Double fertilizerDoseMl;
    private Long recheckDispensersAfterMs;
    private Long restartCounter;
    private Boolean dispensersEnable;
    private Boolean sensorsEnable;
    private Boolean autotime;
    private String timeZone;
    private String wifiSsid;
    private String wifiPass;
    private LocalDateTime microcontrollerTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        HydroponicSettings that = (HydroponicSettings) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
