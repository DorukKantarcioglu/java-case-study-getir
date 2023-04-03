package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.request.BookOrderRequest;
import com.getir.readingisgood.request.OrderRequest;
import com.getir.readingisgood.response.BookOrderResponse;
import com.getir.readingisgood.response.OrderResponse;
import com.getir.readingisgood.service.OrderService;
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

import java.util.*;

@WebMvcTest(value = OrderController.class)
public class OrderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OrderService orderService;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(orderService);
    }

    @Test
    void testPlaceOrder() throws Exception {
        List<BookOrderRequest> bookOrderRequests =
                List.of(new BookOrderRequest(1L, 10L), new BookOrderRequest(2L, 15L));
        OrderRequest orderRequest = new OrderRequest(1L, bookOrderRequests);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(orderRequest);

        List<BookOrderResponse> bookOrderResponses = List.of(
                new BookOrderResponse(1L, 1L, 10L),
                new BookOrderResponse(2L, 2L, 15L));
        OrderResponse orderResponse = new OrderResponse(1L, 1L, new Date(), bookOrderResponses);

        Mockito.when(orderService.placeOrder(Mockito.any(OrderRequest.class))).thenReturn(orderResponse);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/orders").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        Mockito.verify(orderService).placeOrder(Mockito.any(OrderRequest.class));
    }

    @Test
    void testGetOrder() throws Exception {
        OrderResponse orderResponse = new OrderResponse(1L, 1L, new Date(), new ArrayList<>());
        Mockito.when(orderService.getOrder(1L)).thenReturn(orderResponse);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/" + 1))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.verify(orderService).getOrder(1L);
    }

    @Test
    void testGetOrdersBetweenDates() throws Exception {
        List<OrderResponse> orderResponses = new ArrayList<>();
        Calendar calendar = new GregorianCalendar(2023, Calendar.MAY, 3, 3, 0);

        Date startDate = calendar.getTime();

        calendar.set(Calendar.MONTH, 3);
        Date endDate = calendar.getTime();

        Mockito.when(orderService.getOrdersBetweenDates(startDate, endDate)).thenReturn(orderResponses);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/orders?" + "startDate=2023-05-03&endDate=2023-04-03"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.verify(orderService).getOrdersBetweenDates(startDate, endDate);
    }
}
