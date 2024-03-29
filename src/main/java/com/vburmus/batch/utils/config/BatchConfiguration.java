package com.vburmus.batch.utils.config;

import com.vburmus.batch.car.mapper.CarFieldSetMapper;
import com.vburmus.batch.car.model.Car;
import com.vburmus.batch.car.model.CarDTO;
import com.vburmus.batch.car.processor.CarProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {
    private final EntityManagerFactory entityManagerFactory;
    private final CarProcessor carProcessor;
    private final CarFieldSetMapper customFieldSetMapper;
    private final ParseSkipPolicy parseSkipPolicy;

    @Bean
    public Job importCarJob(JobRepository jobRepository, Step parseCars) {
        return new JobBuilder("carParserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(parseCars)
                .build();
    }

    @Bean
    public LineMapper<CarDTO> lineMapper() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("brand", "model", "price_in_pln", "mileage", "gearbox", "engine_capacity", "fuel_type",
                "city", "voivodeship", "year");
        DefaultLineMapper<CarDTO> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(customFieldSetMapper);
        return lineMapper;
    }

    @Bean
    public FlatFileItemReader<CarDTO> carReader() {
        return new FlatFileItemReaderBuilder<CarDTO>()
                .name("carItemReader")
                .resource(new ClassPathResource("car_data.csv"))
                .lineMapper(lineMapper())
                .linesToSkip(1)
                .targetType(CarDTO.class)
                .build();
    }

    @Bean
    public Step parseCars(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("parseCarsStep", jobRepository)
                .<CarDTO, Car>chunk(10, transactionManager)
                .reader(carReader())
                .processor(carProcessor)
                .writer(carItemWriter())
                .faultTolerant()
                .skipPolicy(parseSkipPolicy)
                .build();
    }

    @Bean
    public JpaItemWriter<Car> carItemWriter() {
        JpaItemWriter<Car> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}