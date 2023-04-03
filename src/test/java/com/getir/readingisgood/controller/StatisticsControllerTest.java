package com.getir.readingisgood.controller;

import com.getir.readingisgood.response.StatisticsResponse;
import com.getir.readingisgood.service.StatisticsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(value = StatisticsController.class)
public class StatisticsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private StatisticsService statisticsService;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(statisticsService);
    }

    @Test
    void testGetMonthlyOrderStatistics() throws Exception {
        List<StatisticsResponse> statisticsResponses = new ArrayList<>();
        Mockito.when(statisticsService.getMonthlyOrderStatistics(1L)).thenReturn(statisticsResponses);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/statistics/customers/" + 1))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.verify(statisticsService).getMonthlyOrderStatistics(1L);
    }
}
