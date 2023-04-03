package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.request.BookOrderRequest;
import com.getir.readingisgood.request.OrderRequest;
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
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    void testPlaceOrder() {
        Book book1 = new Book("isbn1", "title1", "author1", 15.0, 50L);
        Book book2 = new Book("isbn2", "title2", "author2", 30.0, 100L);
        book1.setId(1L);
        book2.setId(2L);

        Customer customer = new Customer("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");
        customer.setId(1L);

        List<BookOrderRequest> bookOrderRequests =
                List.of(new BookOrderRequest(1L, 10L), new BookOrderRequest(2L, 15L));
        OrderRequest orderRequest = new OrderRequest(1L, bookOrderRequests);

        Order order = new Order(customer);

        List<BookOrder> bookOrders = List.of(new BookOrder(book1, order, 10L), new BookOrder(book2, order, 15L));

        order.setBookOrders(bookOrders);

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        Mockito.when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        OrderResponse orderResponse = orderService.placeOrder(orderRequest);

        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(2, orderResponse.getBookOrders().size());
        Assertions.assertEquals(1L, orderResponse.getCustomerId());
        Assertions.assertEquals(1L, orderResponse.getBookOrders().get(0).getBookId());
        Assertions.assertEquals(2L, orderResponse.getBookOrders().get(1).getBookId());
        Assertions.assertEquals(10L, orderResponse.getBookOrders().get(0).getCount());
        Assertions.assertEquals(15L, orderResponse.getBookOrders().get(1).getCount());
    }

    @Test
    void testGetOrder() {
        Customer customer = new Customer("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");
        customer.setId(1L);

        Book book1 = new Book("isbn1", "title1", "author1", 15.0, 50L);
        Book book2 = new Book("isbn2", "title2", "author2", 30.0, 100L);
        book1.setId(1L);
        book2.setId(2L);

        Order order = new Order(customer);
        List<BookOrder> bookOrders = List.of(new BookOrder(book1, order, 10L), new BookOrder(book2, order, 15L));
        order.setId(1L);
        order.setBookOrders(bookOrders);

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponse orderResponse = orderService.getOrder(1L);

        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(2, orderResponse.getBookOrders().size());
        Assertions.assertEquals(1L, orderResponse.getCustomerId());
        Assertions.assertEquals(1L, orderResponse.getBookOrders().get(0).getBookId());
        Assertions.assertEquals(2L, orderResponse.getBookOrders().get(1).getBookId());
        Assertions.assertEquals(10L, orderResponse.getBookOrders().get(0).getCount());
        Assertions.assertEquals(15L, orderResponse.getBookOrders().get(1).getCount());
    }

    @Test
    void getOrderBetweenDates() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 3);
        Date startDate = calendar.getTime();

        calendar.set(2023, Calendar.MAY, 3);
        Date endDate = calendar.getTime();

        Customer customer = new Customer("doruk@kantarcioglu.com", "Doruk Kantarcioglu", "Bilkent");
        customer.setId(1L);

        Book book1 = new Book("isbn1", "title1", "author1", 15.0, 50L);
        Book book2 = new Book("isbn2", "title2", "author2", 30.0, 100L);
        book1.setId(1L);
        book2.setId(2L);

        Order order = new Order(customer);
        List<BookOrder> bookOrders = List.of(new BookOrder(book1, order, 10L), new BookOrder(book2, order, 15L));
        order.setId(1L);
        order.setBookOrders(bookOrders);
        order.setCreatedAt(startDate);

        Page<Order> orderPage = new PageImpl<>(List.of(order));

        Mockito.when(orderRepository.findAllByCreatedAtBetween(startDate, endDate, Pageable.unpaged()))
                .thenReturn(orderPage);

        List<OrderResponse> orderResponses = orderService.getOrdersBetweenDates(startDate, endDate);

        Assertions.assertNotNull(orderResponses);
        Assertions.assertEquals(1, orderResponses.size());
        Assertions.assertEquals(2, orderResponses.get(0).getBookOrders().size());
        Assertions.assertEquals(1, orderResponses.get(0).getCustomerId());
    }
}
