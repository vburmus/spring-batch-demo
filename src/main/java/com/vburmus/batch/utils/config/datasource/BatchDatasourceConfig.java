package com.vburmus.batch.utils.config.datasource;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BatchDatasourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.batch")
    public DataSourceProperties batchProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @BatchDataSource
    public DataSource batchDatasource(DataSourceProperties batchProperties) {
        return batchProperties
                .initializeDataSourceBuilder()
                .build();
    }
}