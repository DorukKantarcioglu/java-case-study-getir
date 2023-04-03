package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.exception.EntityAlreadyExistsException;
import com.getir.readingisgood.exception.EntityNotFoundException;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.request.AddCustomerRequest;
import com.getir.readingisgood.response.CustomerResponse;
import com.getir.readingisgood.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public CustomerResponse addCustomer(AddCustomerRequest addCustomerRequest) {
        if (customerRepository.existsByEmail(addCustomerRequest.getEmail())) {
            throw new EntityAlreadyExistsException("Customer with email "
                    + addCustomerRequest.getEmail() + " already exists.");
        }
        Customer customer = customerRepository.save(new Customer(addCustomerRequest.getEmail(),
                addCustomerRequest.getName(), addCustomerRequest.getAddress()));
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getName(), customer.getAddress());
    }

    public List<OrderResponse> getCustomerOrders(Long id, Integer page, Integer size) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " does not exist."));
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findByCustomer(customer, pageable);
        return orderPage.stream().map(OrderServiceImpl::getOrderResponse).collect(Collectors.toList());
    }
}
