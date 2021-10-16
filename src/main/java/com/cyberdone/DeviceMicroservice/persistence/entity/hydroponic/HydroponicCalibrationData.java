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
@Table(name = "HYDROPONIC_CALIBRATION_DATA")
public class HydroponicCalibrationData {
    public Double tdsCalibrationCoefficientValue;
    public Integer tdsOversampling;
    public Integer phCalibrationValuePoint;
    public Double phCalibrationEtalonValue1;
    public Double phCalibrationEtalonValue2;
    public Long phCalibrationAdcValue1;
    public Long phCalibrationAdcValue2;
    public Double phCalibrationSlope;
    public Long phCalibrationValueOffset;
    public Integer phOversampling;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    @CreationTimestamp
    private LocalDateTime receiveTime;
    private LocalDateTime microcontrollerTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        HydroponicCalibrationData that = (HydroponicCalibrationData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
