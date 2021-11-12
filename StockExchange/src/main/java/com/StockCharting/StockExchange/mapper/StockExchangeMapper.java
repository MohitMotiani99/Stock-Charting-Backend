package com.StockCharting.StockExchange.mapper;

import com.StockCharting.StockExchange.dto.StockExchangeDTO;
import com.StockCharting.StockExchange.entity.StockExchange;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class StockExchangeMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(StockExchange.class, StockExchangeDTO.class)
                .fieldAToB("stockExchangeId","stockExchangeId")
                .byDefault());
    }
}
