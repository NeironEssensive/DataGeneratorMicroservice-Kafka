package org.example.datageneratormicroservice.web.mapper;

import org.example.datageneratormicroservice.model.Data;
import org.example.datageneratormicroservice.web.dto.DataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataMapper extends Mappable<Data, DataDto> {
}
