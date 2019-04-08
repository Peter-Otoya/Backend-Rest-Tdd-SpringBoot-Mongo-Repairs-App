/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.services;

import com.otoya.pedro.mechanicworkshop.domain.schedule.Schedule;
import com.otoya.pedro.mechanicworkshop.domain.workstation.Workstation;

import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public interface WorkstationService {
    public List<Workstation> getWorkstations();
    public Map<String, Schedule> calculateSchedule();
}
