package br.caixa.loterias.mapper.support;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public abstract class LegacyDateMapper {

    @Named("toDate")
    public Date toDate(LocalDateTime value) {
        if (value == null) {
            return null;
        }

        return Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }
}
