package com.StockCharting.StockPrice.mapper;

import com.StockCharting.StockPrice.dto.StockPriceDTO;
import com.StockCharting.StockPrice.entity.StockPrice;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class StockPriceMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(StockPrice.class, StockPriceDTO.class)
                .fieldAToB("stockPriceId","stockPriceId")
                .byDefault());
    }
}
