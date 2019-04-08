/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otoya.pedro.mechanicworkshop.controllers;

import com.otoya.pedro.mechanicworkshop.Utils.MyUtils;
import com.otoya.pedro.mechanicworkshop.domain.order.Order;
import com.otoya.pedro.mechanicworkshop.domain.repair.Repair;
import com.otoya.pedro.mechanicworkshop.services.OrderService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PC
 */

@RestController
public class OrderRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestController.class.getName());

    @Autowired
    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/api/orders", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Gets All Orders", response = Order.class, responseContainer = "List")
    public List<Order> getEmployees() {
        List<Order> employees = orderService.getOrders();
        return employees;
    }

    @GetMapping("/api/orders/{orderId}")
    @ApiOperation(value = "Gets An Order By Id", response = Order.class, responseContainer = "Order")
    public Order getOrder(@PathVariable(name = "orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @RequestMapping(value = "/api/orders", method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation("Inserts a new order.")
    @ResponseBody
    public Map<String, String> insertOrder(@RequestBody Order order) {
        HashMap<String, String> resp = new HashMap<>();

        Long insertedOrderId = null;
        try {
            insertedOrderId = this.orderService.insert(order);
        } catch (Exception e) {
            resp.put("error", e.getMessage());
            return resp;
        }

        resp.put("orderId", String.valueOf(insertedOrderId));
        resp.put("createdDate", order.getCreationDate().toString());
        return resp;
    }

    @PutMapping("api/orders/{orderId}/repairs/completed")
    @ApiOperation(value = "Updates status of specific repairs within an order")
    @ResponseBody
    public Map<String, String> updateOrderRepairs(@RequestBody List<Repair> completedRepairs,
                                                  @PathVariable(name = "orderId") Long orderId) {
        HashMap<String, String> resp = new HashMap<>();
        List<Integer> updatedRepairIds;
        try {
            updatedRepairIds = orderService.updateOrderRepairs(orderId, completedRepairs);
        } catch (Exception e) {
            resp.put("error", e.getMessage());
            LOGGER.error(e.getMessage(),e);
            return resp;
        }
        resp.put("orderId", String.valueOf(orderId));
        resp.put("result", MyUtils.stringify(updatedRepairIds));
        return resp;
    }

}
