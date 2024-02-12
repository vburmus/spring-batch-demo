package com.vburmus.batch.car.model;

import lombok.Builder;

@Builder
public record CarDTO(String brand,
                     String model,
                     Float price,
                     Float mileage,
                     String gearbox,
                     Float engineCapacity,
                     String fuelType,
                     String city,
                     String region,
                     Integer year) {
}