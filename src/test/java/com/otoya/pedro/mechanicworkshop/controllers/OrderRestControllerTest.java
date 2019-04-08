package com.otoya.pedro.mechanicworkshop.controllers;

import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.otoya.pedro.mechanicworkshop.domain.order.Order;
import com.otoya.pedro.mechanicworkshop.domain.repair.Repair;
import com.otoya.pedro.mechanicworkshop.domain.vehicle.Vehicle;
import com.otoya.pedro.mechanicworkshop.services.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    private final static JacksonJsonProvider JACKSON_JSON_PROVIDER = new JacksonJsonProvider();

    private String testOrderJson;

    private Order testOrder;

    @Before
    public void setup() {
        Order order = new Order();
        order.setId(1L);
        Vehicle vehicle = new Vehicle();
        vehicle.setPlates("GNU738");
        vehicle.setCategory("CAR");
        vehicle.setNumOfSeats(4);
        order.setVehicle(vehicle);

        Repair r = new Repair();
        r.setId(1);
        r.setType("PROC_A");
        r.setEstTimeInHours(5.0);
        order.setRepairs(Collections.singletonList(r));

        String json = new JacksonJsonProvider().toJson(order);

        testOrder = order;
        testOrderJson = "{\"id\":1,\"vehicle\":{\"plates\":\"GNU738\",\"category\":\"CAR\",\"numOfSeats\":4},\"repairs\":[{\"id\":1,\"type\":\"PROC_A\",\"estTimeInHours\":null}]}";

    }

    // 1. Insert order test.
    //
    // Requirements:
    //  a. Is there is an error status message 200 Error Check some message is sent.
    //  b. If no error then check msg status message 200  String "Ok" should be sent
    //  c. Insert an order with at least a repair specifying a wrong type.
    //  d. Insert an order for a vehicle with a wrong category
    //
    @Test
    public void case_1a_insertOrder() throws Exception {
        //Arrange

        when(this.orderService.insert(Mockito.any(Order.class))).thenReturn(1L);
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();

        String json = "{\"id\":1,\"vehicle\":{\"plates\":\"GNU738\",\"category\":\"CAR\",\"numOfSeats\":4}," +
                "\"repairs\":[{\"id\":1,\"type\":\"PROC_A\",\"estTimeInHours\":null}]}";

//        String jsonResponseBody =
//                String.format("{\"orderId\":\"1\", \"createdDate\":\"%s\"}" ,
//                        String.valueOf(testOrder.getCreationDate()));


        //Act and Assert
        MvcResult mvcResult = this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("\"orderId\":\"1\"" ));

    }

    @Test
    public void case_1b_insertOrder_error() throws Exception {
        //Arrange
        // Check with empty order to make sure error message is sent.
        when(this.orderService.insert(Mockito.any(Order.class))).thenThrow(Exception.class);

        //Act and Assert
        MvcResult mvcResult = this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk()).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("error"));
    }


    @Test
    public void case_1c_insert_order_with_bad_repair_type() throws Exception {
        String jsonRequestBody = "{\"id\":1,\"vehicle\":{\"plates\":\"GNU738\",\"numOfSeats\":4,\"category\":\"CAR\"}," +
                "\"repairs\":[{\"id\":1,\"type\":\"PROC_NON_EXISTENT\"}]}";

        //Act and Assert
        this.mockMvc.perform(post("/api/orders", jsonRequestBody))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void case_1d_insert_order_with_bad_vehicle_category() throws Exception {
        // Boat is not an accepted vehicle type.
        String json = "{\"id\":1,\"vehicle\":{\"plates\":\"GNU738\",\"numOfSeats\":4,\"category\":\"BOAT\"}," +
                "\"repairs\":[{\"id\":1,\"type\":\"PROC_A\"}]}";

        //Act and Assert
        this.mockMvc.perform(post("/api/orders", json))
                .andExpect(status().is4xxClientError());
    }

    // 2. Update order repairs test. (Takes list of repairs and updates them within embedded doc)
    //
    // Output:
    //  a. List of orders is updated correctly on a specific order. (Check update is called).
    //  b.
    @Test
    public void case_1a_updateOrderRepairs_Ok() throws Exception {
        //Arrange
        when(orderService.updateOrderRepairs(Mockito.anyLong(), Mockito.anyList())).thenReturn(Arrays.asList(1,2));
        String json = "[{\"id\":1,\"type\":\"PROC_A\",\"estTimeInHours\":4,\"status\":\"CREATED\"}, {\"id\":2," +
                "\"type\":\"PROC_B\",\"estTimeInHours\":4,\"status\":\"CREATED\"}, " +
                "{\"id\":3,\"type\":\"PROC_C\",\"estTimeInHours\":4,\"status\":\"CREATED\"} ]";

        //Act
        MvcResult mvcResult = this.mockMvc.perform(put("/api/orders/1/repairs/completed?orderId=1")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("[1,2]"));
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("\"orderId\":\"1\""));
    }

    @Test
    public void case_2b_updateOrderRepairs_error() throws Exception {
        //Arrange
        // Check with empty order to make sure error message is sent.
        when(orderService.updateOrderRepairs(Mockito.anyLong(), Mockito.anyList())).thenThrow(Exception.class);
        String json = "[{\"id\":1,\"type\":\"PROC_A\",\"estTimeInHours\":4,\"status\":\"CREATED\"}, {\"id\":2," +
                "\"type\":\"PROC_B\",\"estTimeInHours\":4,\"status\":\"CREATED\"}, " +
                "{\"id\":3,\"type\":\"PROC_C\",\"estTimeInHours\":4,\"status\":\"CREATED\"} ]";
        //Act
        MvcResult mvcResult = this.mockMvc.perform(put("/api/orders/1/repairs/completed?orderId=1")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

       //Assert
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("error"));
    }

}