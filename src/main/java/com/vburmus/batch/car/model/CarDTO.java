package com.vburmus.batch.car.model;

import lombok.Builder;

@Builder
public record CarDTO(String brandName,
                     String model,
                     Float price,
                     Float mileage,
                     String gearbox,
                     Float engineCapacity,
                     String fuelTypeName,
                     String cityName,
                     String regionName,
                     Integer productionYear) {
}