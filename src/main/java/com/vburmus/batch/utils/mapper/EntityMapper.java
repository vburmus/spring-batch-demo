package com.vburmus.batch.utils.mapper;

import com.vburmus.batch.brand.Brand;
import com.vburmus.batch.brand.BrandRepository;
import com.vburmus.batch.car.model.Car;
import com.vburmus.batch.car.model.CarDTO;
import com.vburmus.batch.car.model.FuelType;
import com.vburmus.batch.car.model.Gearbox;
import com.vburmus.batch.city.City;
import com.vburmus.batch.city.CityRepository;
import com.vburmus.batch.region.Region;
import com.vburmus.batch.region.RegionRepository;
import org.apache.commons.text.WordUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EntityMapper {
    @Autowired
    protected CityRepository cityRepository;
    @Autowired
    protected BrandRepository brandRepository;
    @Autowired
    protected RegionRepository regionRepository;

    @Mapping(target = "gearbox", source = "gearbox", qualifiedByName = "toGearbox")
    @Mapping(target = "fuelType", source = "fuelTypeName", qualifiedByName = "toFuelType")
    @Mapping(target = "city", source = "cityName", qualifiedByName = "toCity")
    @Mapping(target = "region", source = "regionName", qualifiedByName = "toRegion")
    @Mapping(target = "brand", source = "brandName", qualifiedByName = "toBrand")
    public abstract Car toCar(CarDTO carDTO);

    @Named("toCity")
    public City toCity(String cityName) {
        return cityRepository
                .findByName(cityName)
                .orElseGet(() -> {
                    City newCity = new City(cityName);
                    return cityRepository.save(newCity);
                });
    }

    @Named("toBrand")
    public Brand toBrand(String brandName) {
        String brandNameValue = WordUtils.capitalize(brandName.replace("-", " "));
        return brandRepository
                .findByName(brandNameValue)
                .orElseGet(() -> {
                    Brand newBrand = new Brand(brandNameValue);
                    return brandRepository.save(newBrand);
                });
    }

    @Named("toRegion")
    public Region toRegion(String regionName) {
        return regionRepository
                .findByName(regionName)
                .orElseGet(() -> {
                    Region newRegion = new Region(regionName);
                    return regionRepository.save(newRegion);
                });
    }

    @Named("toGearbox")
    public Gearbox toGearbox(String gearbox) {
        String gearboxValue = gearbox.toUpperCase();
        return Enum.valueOf(Gearbox.class, gearboxValue);
    }

    @Named("toFuelType")
    public FuelType toFuelType(String fuelType) {
        String fuelTypeValue = fuelType
                .toUpperCase()
                .replace("+", "_");
        return Enum.valueOf(FuelType.class, fuelTypeValue);
    }
}