package com.otoya.pedro.mechanicworkshop.services;

import com.otoya.pedro.mechanicworkshop.domain.order.Order;
import com.otoya.pedro.mechanicworkshop.domain.repair.Repair;
import com.otoya.pedro.mechanicworkshop.domain.repair.RepairMetadata;
import com.otoya.pedro.mechanicworkshop.domain.vehicle.Vehicle;
import com.otoya.pedro.mechanicworkshop.helpers.OrderHelper;
import com.otoya.pedro.mechanicworkshop.exceptions.FailedValidationException;
import com.otoya.pedro.mechanicworkshop.repositories.OrderRepository;
import com.otoya.pedro.mechanicworkshop.services.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// In this case it's better to perform tests without springboot context - so they execute faster.

public class OrderServiceTest {

    OrderServiceImpl orderService;

    OrderRepository orderRepository;

    OrderHelper orderHelper;

    private Order testOrder;

    @Before
    public void setUp() {
        orderService = new OrderServiceImpl();

        orderRepository = Mockito.mock(OrderRepository.class);
        orderService.setOrderRepository(orderRepository);

        orderHelper = Mockito.mock(OrderHelper.class);
        orderService.setOrderHelper(orderHelper);

        testOrder = new Order();
        testOrder.setId(1L);
        Vehicle vehicle = new Vehicle();
        vehicle.setPlates("GNU738");
        vehicle.setCategory("CAR");
        vehicle.setNumOfSeats(4);
        testOrder.setVehicle(vehicle);

        Repair r = new Repair();
        r.setId(1);
        r.setType("PROC_A");
        r.setEstTimeInHours(5.0);
        testOrder.setRepairs(Collections.singletonList(r));
    }


    // 1. Insert order
    //      a) Normal insert no problems
    //      b) Insert an order with no repairs - either repairs is null or isEmpty
    //      c) Insert an order with no vehicle
    //      d) If the order's vehicle number of seats is absent - (better to tested on helper but for simplicity done here)

    @Test
    public void case_1a_insertOrder_Ok() throws Exception {
        //Arrange
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        //Act
        Long result = orderService.insert(testOrder);

        //Assert
        Mockito.verify(orderHelper, times(1)).estimateRepairHours(any(Order.class));
        Assert.assertEquals(testOrder.getId(), result);
    }

    @Test(expected = FailedValidationException.class)
    public void case_1b_insertOrder_no_repairs() throws Exception {
        // Should throw exception.
        testOrder.setRepairs(Collections.emptyList());
        Long result = orderService.insert(testOrder);
    }

    @Test(expected = FailedValidationException.class)
    public void case_1c_insertOrder_no_vehicle() throws Exception {
        // Should throw exception.
        testOrder.setVehicle(null);
        Long result = orderService.insert(testOrder);
    }

    @Test(expected = FailedValidationException.class)
    public void case_1d_insertOrder_no_vehicle_seats() throws Exception {
        // Should throw exception.
        testOrder.getVehicle().setNumOfSeats(null);
        Long result = orderService.insert(testOrder);
    }

    // 2. Update Completed Orders
    //      a) Normal complete order update no problems
    //      TODO (List Error cases pending and develop)...[Failed validation example]

    @Test
    public void case_2a_update_completed_orders_Ok() throws Exception {
        //Arrange
        Order o = Mockito.mock(Order.class);
        Optional<Order> orderOptional = Optional.of(o);
        when(orderRepository.findById(1L)).thenReturn(orderOptional);

        Repair r1 = new Repair(1, RepairMetadata.Type.PROC_A, 2.0);
        r1.setInProgress();
        Repair r2 = new Repair(2, RepairMetadata.Type.PROC_B, 2.0);
        r2.setInProgress();

        when(o.getRepairs()).thenReturn(
                Arrays.asList(r1,r2, new Repair(3, RepairMetadata.Type.PROC_C, 2.0)
        ));

        //Act
        orderService.updateOrderRepairs(1L,
                Arrays.asList(
                        new Repair(1, RepairMetadata.Type.PROC_A, 2.0),
                        new Repair(2, RepairMetadata.Type.PROC_B, 2.0)
                ));

        //Assert
        Assert.assertEquals(r1.getStatus(), RepairMetadata.Status.COMPLETED);
        Assert.assertEquals(r2.getStatus(), RepairMetadata.Status.COMPLETED);

    }

}