package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.response.StatisticsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {
    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void testGetMonthlyOrderStatistics() {
        Customer customer = new Customer("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");
        customer.setId(1L);

        Order order1 = new Order(customer);
        Order order2 = new Order(customer);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MARCH, 3);
        order1.setCreatedAt(calendar.getTime());

        Book book1 = new Book("isbn1", "title1", "author1", 15.0, 50L);
        Book book2 = new Book("isbn2", "title2", "author2", 30.0, 100L);

        BookOrder bookOrder1 = new BookOrder(book1, order1, 5L);
        BookOrder bookOrder2 = new BookOrder(book2, order1, 10L);
        BookOrder bookOrder3 = new BookOrder(book2, order2, 15L);

        order1.setBookOrders(List.of(bookOrder1, bookOrder2));
        order2.setBookOrders(List.of(bookOrder3));

        calendar.set(2023, Calendar.APRIL, 3);
        order2.setCreatedAt(calendar.getTime());


        Page<Order> page = new PageImpl<>(List.of(order1, order2));

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Mockito.when(orderRepository.findByCustomer(customer, Pageable.unpaged())).thenReturn(page);

        List<StatisticsResponse> statisticsResponses = statisticsService.getMonthlyOrderStatistics(1L);

        Assertions.assertNotNull(statisticsResponses);
        Assertions.assertEquals(2, statisticsResponses.size());
        Assertions.assertTrue(statisticsResponses.stream()
                .anyMatch(statisticsResponse -> statisticsResponse.getMonth().equals("APRIL")));
        Assertions.assertTrue(statisticsResponses.stream()
                .anyMatch(statisticsResponse -> statisticsResponse.getMonth().equals("MARCH")));
        Assertions.assertTrue(statisticsResponses.stream()
                .anyMatch(statisticsResponse -> statisticsResponse.getTotalBookCount() == 15));
        Assertions.assertTrue(statisticsResponses.stream()
                .anyMatch(statisticsResponse -> statisticsResponse.getTotalOrderCount() == 1));
        Assertions.assertTrue(statisticsResponses.stream()
                .anyMatch(statisticsResponse -> statisticsResponse.getTotalPurchasedAmount() == 375));
        Assertions.assertTrue(statisticsResponses.stream()
                .anyMatch(statisticsResponse -> statisticsResponse.getTotalPurchasedAmount() == 450));

    }
}
