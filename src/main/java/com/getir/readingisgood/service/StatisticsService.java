package com.getir.readingisgood.service;

import com.getir.readingisgood.response.StatisticsResponse;

import java.util.List;

public interface StatisticsService {
    List<StatisticsResponse> getMonthlyOrderStatistics(Long customerId);
}
