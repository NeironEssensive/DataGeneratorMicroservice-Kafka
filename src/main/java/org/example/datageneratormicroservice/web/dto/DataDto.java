package org.example.datageneratormicroservice.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.datageneratormicroservice.model.Data;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class DataDto {
    private Long sensorId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private double measurement;
    private Data.MeasurementType measurementType;

}
