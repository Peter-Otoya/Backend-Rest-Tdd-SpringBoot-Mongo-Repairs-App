/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.services;

import com.otoya.pedro.mechanicworkshop.domain.order.Order;
import com.otoya.pedro.mechanicworkshop.domain.repair.Repair;

import java.util.List;

/**
 *
 * @author PC
 */
public interface OrderService {
    public List<Order> getOrders();

    public Order getOrder(Long orderId);

    public Long insert(Order order) throws Exception;

    public List<Integer> updateOrderRepairs(Long orderId, List<Repair> completedRepairs) throws Exception;
}
