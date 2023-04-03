package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.request.AddCustomerRequest;
import com.getir.readingisgood.response.CustomerResponse;
import com.getir.readingisgood.response.OrderResponse;
import com.getir.readingisgood.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomerService customerService;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(customerService);
    }

    @Test
    void testAddCustomer() throws Exception {
        AddCustomerRequest addCustomerRequest =
                new AddCustomerRequest("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(addCustomerRequest);

        CustomerResponse customerResponse =
                new CustomerResponse(1L, "doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");
        Mockito.when(customerService.addCustomer(Mockito.any(AddCustomerRequest.class))).thenReturn(customerResponse);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/customers").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        Mockito.verify(customerService).addCustomer(Mockito.any(AddCustomerRequest.class));
    }

    @Test
    void testGetCustomerOrders() throws Exception {
        List<OrderResponse> orderResponses = new ArrayList<>();

        Mockito.when(customerService.getCustomerOrders(1L, 0, 20)).thenReturn(orderResponses);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + 1 + "/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.verify(customerService).getCustomerOrders(1L, 0, 20);
    }
}
