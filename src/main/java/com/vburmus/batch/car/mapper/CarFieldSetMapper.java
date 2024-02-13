package com.vburmus.batch.car.mapper;

import com.vburmus.batch.car.model.CarDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarFieldSetMapper implements FieldSetMapper<CarDTO> {
    @Override
    public CarDTO mapFieldSet(FieldSet fieldSet) {
        log.debug("Parsing {}", fieldSet.toString());

        return CarDTO
                .builder()
                .brandName(fieldSet.readString("brand"))
                .model(fieldSet.readString("model"))
                .price(fieldSet.readFloat("price_in_pln"))
                .mileage(parseMileage(fieldSet))
                .gearbox(fieldSet.readString("gearbox"))
                .engineCapacity(parseEngineCapacity(fieldSet))
                .fuelTypeName(fieldSet.readString("fuel_type"))
                .cityName(fieldSet.readString("city"))
                .regionName(fieldSet.readString("voivodeship"))
                .productionYear(fieldSet.readInt("year"))
                .build();
    }

    private Float parseEngineCapacity(FieldSet fieldSet) {
        return replaceUnitFromFloat(fieldSet, "engine_capacity", "cm3");
    }

    private Float parseMileage(FieldSet fieldSet) {
        return replaceUnitFromFloat(fieldSet, "mileage", "km");
    }

    private Float replaceUnitFromFloat(FieldSet fieldSet, String fieldName, String unit) {
        return Float.valueOf(fieldSet
                .readString(fieldName)
                .replace(unit, "")
                .replaceAll("\\s", ""));
    }
}