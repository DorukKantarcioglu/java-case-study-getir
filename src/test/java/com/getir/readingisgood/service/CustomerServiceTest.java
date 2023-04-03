package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.request.AddCustomerRequest;
import com.getir.readingisgood.response.CustomerResponse;
import com.getir.readingisgood.response.OrderResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void testAddCustomer() {
        AddCustomerRequest addCustomerRequest =
                new AddCustomerRequest("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");

        Customer customer = new Customer("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent University");
        customer.setId(1L);

        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        CustomerResponse customerResponse = customerService.addCustomer(addCustomerRequest);

        Assertions.assertEquals(1, customerResponse.getId());
        Assertions.assertEquals("doruk@kantarcioglu.com", customerResponse.getEmail());
    }

    @Test
    void testGetCustomerOrders() {
        Customer customer = new Customer("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");
        customer.setId(1L);

        Order order1 = new Order(customer);
        Order order2 = new Order(customer);

        Book book1 = new Book("isbn1", "title1", "author1", 15.0, 50L);
        Book book2 = new Book("isbn2", "title2", "author2", 30.0, 100L);
        book1.setId(1L);
        book2.setId(2L);

        BookOrder bookOrder1 = new BookOrder(book1, order1, 5L);
        BookOrder bookOrder2 = new BookOrder(book2, order1, 10L);
        BookOrder bookOrder3 = new BookOrder(book2, order2, 15L);

        order1.setBookOrders(List.of(bookOrder1, bookOrder2));
        order2.setBookOrders(List.of(bookOrder3));

        Page<Order> orderPage = new PageImpl<>(List.of(order1, order2));

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Mockito.when(orderRepository.findByCustomer(customer, PageRequest.of(0, 20))).thenReturn(orderPage);

        List<OrderResponse> orderResponses = customerService.getCustomerOrders(1L,0, 20);

        Assertions.assertNotNull(orderResponses);
        Assertions.assertEquals(2, orderResponses.size());
        Assertions.assertTrue(orderResponses.stream()
                .anyMatch(orderResponse -> orderResponse.getCustomerId() == 1));
    }
}
