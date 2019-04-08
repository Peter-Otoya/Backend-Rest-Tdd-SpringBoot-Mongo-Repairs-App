/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.controllers;

import com.otoya.pedro.mechanicworkshop.domain.schedule.Schedule;
import com.otoya.pedro.mechanicworkshop.domain.workstation.Workstation;
import com.otoya.pedro.mechanicworkshop.services.WorkstationService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author PC
 */

@RestController
public class WorkstationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkstationController.class.getName());

    @Autowired
    private WorkstationService workstationService;

    public void setWorkstationService(WorkstationService workstationService) {
        this.workstationService = workstationService;
    }

    @RequestMapping(value = "/api/workstation", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Gets All Workstations", response = Workstation.class, responseContainer = "List")
    public List<Workstation> getWorkstations() {
        // TODO: Get all workstations.
        return workstationService.getWorkstations();
    }

    @RequestMapping(value = "/api/workstation/schedule", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get daily schedule for all workstations", response = Workstation.class, responseContainer = "List")
    public Map<String, Schedule> getSchedule() {
        return workstationService.calculateSchedule();
    }


}
