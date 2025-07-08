package com.sarth.ticket.config;

import com.sarth.ticket.mappers.EventMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {


    public EventMapper eventMapper() {
        return Mappers.getMapper(EventMapper.class);
    }
}
