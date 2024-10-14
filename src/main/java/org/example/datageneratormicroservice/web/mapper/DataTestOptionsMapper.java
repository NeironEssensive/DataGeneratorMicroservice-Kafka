package org.example.datageneratormicroservice.web.mapper;

import org.example.datageneratormicroservice.model.Data;
import org.example.datageneratormicroservice.model.test.DataTestOptions;
import org.example.datageneratormicroservice.web.dto.DataDto;
import org.example.datageneratormicroservice.web.dto.DataTestOptionsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataTestOptionsMapper extends Mappable<DataTestOptions, DataTestOptionsDto> {
}
