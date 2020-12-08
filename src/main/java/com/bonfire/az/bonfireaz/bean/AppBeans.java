package com.bonfire.az.bonfireaz.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeans {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
