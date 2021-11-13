package com.StockCharting.User.mapper;

import com.StockCharting.User.dto.UserDTO;
import com.StockCharting.User.entity.User;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.registerClassMap(factory.classMap(User.class, UserDTO.class)
                .fieldAToB("userId","userId")
                .fieldAToB("userType","userType")
                .fieldAToB("confirmed","confirmed")
                .byDefault());
    }
}
