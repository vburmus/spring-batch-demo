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
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Brand brand;
    private String model;
    private BigDecimal price;
    private Float mileage;
    private Gearbox gearbox;
    private Float engineCapacity;
    private FuelType fuelType;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private City city;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
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
}