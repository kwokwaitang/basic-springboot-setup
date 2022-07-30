package com.kwt.basicspringbootsetup;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BasicSpringBootSetupApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicSpringBootSetupApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                // To allow ModelMapper to compare private fields in the mapping classes (objects)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Basic SpringBoot setup")
                .pathsToMatch("/mcu-movies/**")
                .build();
    }
}
