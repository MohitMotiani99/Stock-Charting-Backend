package com.StockCharting.Sector.mapper;

import com.StockCharting.Sector.dto.SectorDTO;
import com.StockCharting.Sector.entity.Sector;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class SectorMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(Sector.class, SectorDTO.class)
                .fieldAToB("sectorId","sectorId")
                .byDefault());
    }
}
