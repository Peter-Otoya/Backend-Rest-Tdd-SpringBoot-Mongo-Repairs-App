package com.otoya.pedro.mechanicworkshop;

import com.otoya.pedro.mechanicworkshop.repositories.OrderRepository;
import com.otoya.pedro.mechanicworkshop.repositories.WorkstationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class DataLoader implements ApplicationRunner {

    @Autowired
    private WorkstationRepository workstationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //TODO: Load test data.
    }
}
