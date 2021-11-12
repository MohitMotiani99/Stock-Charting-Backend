package com.StockCharting.StockPrice.mapper;

import com.StockCharting.StockPrice.dto.StockSeries1;
import com.StockCharting.StockPrice.entity.StockPrice;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class StockSeries1Mapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(StockPrice.class, StockSeries1.class)
                .byDefault());
    }
}
