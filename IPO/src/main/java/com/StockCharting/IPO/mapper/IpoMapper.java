package com.StockCharting.IPO.mapper;

import com.StockCharting.IPO.dto.IpoDTO;
import com.StockCharting.IPO.entity.Ipo;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class IpoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(Ipo.class, IpoDTO.class)
                .fieldAToB("ipoId","ipoId")
                .byDefault());
    }
}
