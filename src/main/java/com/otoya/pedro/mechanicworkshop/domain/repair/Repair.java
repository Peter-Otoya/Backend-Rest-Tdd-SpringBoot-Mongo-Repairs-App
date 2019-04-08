/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.domain.repair;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author JavaSolutionsGuide
 */
@JsonDeserialize(as = Repair.class)
@Getter
@Setter
@ToString
public class Repair {

    //-------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------
    private Integer id;
    private RepairMetadata.Type type;
    private Double estTimeInHours;

    @Setter(AccessLevel.NONE)
    private RepairMetadata.Status status;


    //-------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------
    public Repair() {
        this.status = RepairMetadata.Status.CREATED;
    }

    public Repair(Integer id, RepairMetadata.Type type, Double estTimeInHours) {
        this.id = id;
        this.type = type;
        this.status = RepairMetadata.Status.CREATED;
        this.estTimeInHours = estTimeInHours;
    }

    //-------------------------------------------------------------
    // Methods
    //-------------------------------------------------------------

    /**
     * If repairType is not a known repair type then valueOf method will throw unchecked illegalstate exception
     *
     * @param repairType
     */
    @JsonSetter("type")
    public void setType(String repairType) {
        type = RepairMetadata.Type.valueOf(repairType);
    }

    /**
     * Sets in progress
     */
    public void setInProgress() {
        this.status = RepairMetadata.Status.IN_PROGRESS;
    }

    /**
     * Sets completed
     */
    public void setCompleted() {
        if (this.status == RepairMetadata.Status.IN_PROGRESS)
            this.status = RepairMetadata.Status.COMPLETED;
        else
            throw new IllegalStateException("Repair is not IN_PROGRESS");
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Repair) obj).getId();
    }
}
