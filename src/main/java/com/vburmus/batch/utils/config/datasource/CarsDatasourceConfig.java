package com.vburmus.batch.utils.config.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class CarsDatasourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.cars")
    public DataSourceProperties carsProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties carsProperties) {
        return carsProperties
                .initializeDataSourceBuilder()
                .build();
    }
}