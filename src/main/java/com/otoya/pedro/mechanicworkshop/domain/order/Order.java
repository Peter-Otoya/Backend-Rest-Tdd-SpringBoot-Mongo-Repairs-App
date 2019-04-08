/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.domain.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.otoya.pedro.mechanicworkshop.domain.vehicle.Vehicle;
import com.otoya.pedro.mechanicworkshop.domain.repair.Repair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author JavaSolutionsGuide
 *
 */


@Document(collection = "orders")
@JsonDeserialize(as=Order.class)
@Getter
@Setter
@ToString
public class Order {
    @Id
    private Long id;
    private Vehicle vehicle;
    private List<Repair> repairs;

    @Setter(AccessLevel.NONE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date creationDate;

    public Order(){
        creationDate = new Date();
    }

}
