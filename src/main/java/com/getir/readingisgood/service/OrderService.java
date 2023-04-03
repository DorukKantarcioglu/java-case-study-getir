package com.getir.readingisgood.service;

import com.getir.readingisgood.request.OrderRequest;
import com.getir.readingisgood.response.OrderResponse;

import java.util.Date;
import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);

    OrderResponse getOrder(Long id);

    List<OrderResponse> getOrdersBetweenDates(Date startDate, Date endDate);
}
