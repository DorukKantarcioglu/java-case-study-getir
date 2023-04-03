package com.getir.readingisgood.controller;

import com.getir.readingisgood.request.AddCustomerRequest;
import com.getir.readingisgood.response.CustomerResponse;
import com.getir.readingisgood.response.OrderResponse;
import com.getir.readingisgood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/customers")
@Validated
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody AddCustomerRequest addCustomerRequest) {
        return new ResponseEntity<>(customerService.addCustomer(addCustomerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable Long id,
                                                                 @PositiveOrZero @RequestParam(defaultValue = "0")
                                                                 Integer page,
                                                                 @Positive @RequestParam(defaultValue = "20")
                                                                     Integer size) {
        return new ResponseEntity<>(customerService.getCustomerOrders(id, page, size), HttpStatus.OK);
    }
}
