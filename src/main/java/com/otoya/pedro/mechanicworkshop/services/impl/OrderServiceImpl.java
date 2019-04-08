package com.otoya.pedro.mechanicworkshop.services.impl;

import com.otoya.pedro.mechanicworkshop.domain.order.Order;
import com.otoya.pedro.mechanicworkshop.domain.repair.Repair;
import com.otoya.pedro.mechanicworkshop.helpers.OrderHelper;
import com.otoya.pedro.mechanicworkshop.exceptions.FailedValidationException;
import com.otoya.pedro.mechanicworkshop.repositories.OrderRepository;
import com.otoya.pedro.mechanicworkshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    //--------------------------------------------------------------
    // Attributes
    //--------------------------------------------------------------
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderHelper orderHelper;

    //--------------------------------------------------------------
    // Methods
    //--------------------------------------------------------------
    public void setOrderRepository(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public void setOrderHelper(OrderHelper orderHelper){
        this.orderHelper = orderHelper;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    /**
     * Inserts a new order into the database.
     * @param order
     * @return Order id
     * @throws Exception If the order has no vehicle or no repairs
     */
    @Override
    public Long insert(Order order) throws Exception {
        if(order.getRepairs() == null || order.getRepairs().isEmpty()){
            throw new FailedValidationException("Order has no repairs");
        }
        if(order.getVehicle() == null){
            throw new FailedValidationException("Order has no vehicle");
        }
        if(order.getVehicle().getNumOfSeats() == null || order.getVehicle().getNumOfSeats() == 0){
            throw new FailedValidationException("Order's vehicle has no seats");
        }

        orderHelper.estimateRepairHours(order);
        orderRepository.
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    /**
     * Sets repairs of a particular order to completed status.
     * @param orderId
     * @param completedRepairs
     */
    @Override
    public List<Integer> updateOrderRepairs(Long orderId, List<Repair> completedRepairs) throws Exception {
        Order o = orderRepository.findById(orderId).get();

        List<Repair> repairs = o.getRepairs();
        List<Integer> setToCompletedList = new ArrayList<>();
        for(Repair r1 : completedRepairs){
            for(Repair r2 : repairs){
                if(r1.equals(r2)){
                    r2.setCompleted();
                    setToCompletedList.add(r2.getId());
                }
            }
        }

        // TODO Remove from workstation schedule.

        return setToCompletedList;
    }
}
