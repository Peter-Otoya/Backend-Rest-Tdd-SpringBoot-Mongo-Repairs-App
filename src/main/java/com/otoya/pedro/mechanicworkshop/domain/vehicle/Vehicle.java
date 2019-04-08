/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author JavaSolutionsGuide
 */

@JsonDeserialize(as = Vehicle.class)
@Getter
@Setter
@ToString
public class Vehicle {
    private String plates;

    @Setter(AccessLevel.NONE)
    private VehicleMetadata.Category category;

    private Integer numOfSeats;

    public String getPlates() {
        return plates;
    }

    @JsonSetter("category")
    public void setCategory(String category) {
        this.category = VehicleMetadata.Category.valueOf(category);
    }

}
