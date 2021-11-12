package com.StockCharting.Company.mapper;

import com.StockCharting.Company.dto.CompanyDTO;
import com.StockCharting.Company.entity.Company;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(Company.class, CompanyDTO.class)
                .fieldAToB("companyId","companyId")
                .byDefault());
    }
}
