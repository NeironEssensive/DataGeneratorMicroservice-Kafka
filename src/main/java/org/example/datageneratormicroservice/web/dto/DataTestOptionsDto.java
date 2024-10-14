package org.example.datageneratormicroservice.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.datageneratormicroservice.model.Data;

@NoArgsConstructor
@Getter
@Setter
public class DataTestOptionsDto {

    private int delayInSeconds;
    private Data.MeasurementType[] measurementTypes;

}
