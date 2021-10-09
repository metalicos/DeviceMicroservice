package com.cyberdone.DeviceMicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
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
    private String uuid;
    private Double mlPerMillisecond;
    private Double regulateErrorPhUp;
    private Double regulateErrorPhDown;
    private Double regulateErrorFertilizer;
    private Double phUpDoseMl;
    private Double phDownDoseMl;
    private Double fertilizerDoseMl;
    private Long recheckDosatorsAfterMs;
    private Double setupPhValue;
    private Long setupTdsValue;
    private Double setupTemperatureValue;
    private Boolean deviceEnable;
    private Boolean dosatorsEnable;
    private Boolean sensorsEnable;
    private Long restartCounter;
    private String wifiSSID;
    private String wifiPASS;
    private Date microcontrollerTime;

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
