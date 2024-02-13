package com.vburmus.batch.car.model;

import com.vburmus.batch.brand.Brand;
import com.vburmus.batch.city.City;
import com.vburmus.batch.region.Region;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Brand brand;
    private String model;
    private BigDecimal price;
    private Float mileage;
    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;
    private Float engineCapacity;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private City city;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id",nullable = false)
    private Region region;
    private Integer productionYear;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return new EqualsBuilder()
                .append(id, car.id)
                .append(brand, car.brand)
                .append(model, car.model)
                .append(price, car.price)
                .append(mileage, car.mileage)
                .append(gearbox, car.gearbox)
                .append(engineCapacity, car.engineCapacity)
                .append(fuelType, car.fuelType)
                .append(city, car.city)
                .append(region, car.region)
                .append(productionYear, car.productionYear)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(brand)
                .append(model)
                .append(price)
                .append(mileage)
                .append(gearbox)
                .append(engineCapacity)
                .append(fuelType)
                .append(city)
                .append(region)
                .append(productionYear)
                .toHashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Car{brand=%s, model=%s,price=%f,mileage=%f,gearbox=%s,engineCapacity=%f,fuelType=%s, city=%s}",
                brand,model,price,mileage,gearbox,engineCapacity,fuelType,city.getName());
    }
}