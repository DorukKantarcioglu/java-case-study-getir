package com.getir.readingisgood.service;

import com.getir.readingisgood.request.AddCustomerRequest;
import com.getir.readingisgood.response.CustomerResponse;
import com.getir.readingisgood.response.OrderResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse addCustomer(AddCustomerRequest addCustomerRequest);

    List<OrderResponse> getCustomerOrders(Long id, Integer page, Integer size);
}
