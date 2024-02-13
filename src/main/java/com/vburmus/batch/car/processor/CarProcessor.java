package com.vburmus.batch.car.processor;

import com.vburmus.batch.car.model.Car;
import com.vburmus.batch.car.model.CarDTO;
import com.vburmus.batch.utils.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarProcessor implements ItemProcessor<CarDTO, Car> {
    private final EntityMapper entityMapper;

    @Override
    public Car process(CarDTO carDTO) {
        Car car = entityMapper.toCar(carDTO);
        log.debug("Processing {}", car.toString());
        return car;
    }
}