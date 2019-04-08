package com.otoya.pedro.mechanicworkshop.services.impl;

import com.otoya.pedro.mechanicworkshop.domain.schedule.Schedule;
import com.otoya.pedro.mechanicworkshop.domain.workstation.Workstation;
import com.otoya.pedro.mechanicworkshop.repositories.WorkstationRepository;
import com.otoya.pedro.mechanicworkshop.services.WorkstationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class WorkstationServiceImpl implements WorkstationService {

    @Autowired
    private WorkstationRepository workstationRepository;

    @Override
    public List<Workstation> getWorkstations() {
        return workstationRepository.findAll();
    }

    /**
     * Calculates schedule for the workstations. (Will rebuild
     *
     * @return
     */
    @Override
    public Map<String, Schedule> calculateSchedule() {
        //TODO: Implementation pending
        //Dequeue the first order 'o'
        //try to assign 'o' to the BEST available (slot in this week) workstation that is fitted to gandle that order
        //repeat if 'o' was successfully scheduled, otherwise break.
        return null;
    }

    /**
     * Returns the index of the day in which you should append
     *
     * @param workstations
     * @return
     */
    private int findBestSlot(List<Workstation> workstations) {
        //TODO: Implementation pending
        return -1;
    }
}
