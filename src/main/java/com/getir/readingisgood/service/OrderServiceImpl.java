package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.exception.EntityNotFoundException;
import com.getir.readingisgood.exception.InsufficientEntityException;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.request.BookOrderRequest;
import com.getir.readingisgood.request.OrderRequest;
import com.getir.readingisgood.response.BookOrderResponse;
import com.getir.readingisgood.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final BookRepository bookRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository,
                            BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID "
                        + orderRequest.getCustomerId() + " does not exist."));
        Order order = new Order(customer);
        List<Book> books = new ArrayList<>();
        List<BookOrder> bookOrders = new ArrayList<>();
        for (BookOrderRequest bookOrderRequest : orderRequest.getBookOrders()) {
            Book book = bookRepository.findById(bookOrderRequest.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book with ID "
                            + bookOrderRequest.getBookId() + " does not exist."));
            if (book.getStock() < bookOrderRequest.getBookCount()) {
                throw new InsufficientEntityException("Requested book count " + bookOrderRequest.getBookCount()
                        + " is more than the available stock.");
            }
            book.setStock(book.getStock() - bookOrderRequest.getBookCount());
            books.add(book);
            bookOrders.add(new BookOrder(book, order, bookOrderRequest.getBookCount()));
        }
        bookRepository.saveAll(books);
        order.setBookOrders(bookOrders);
        order = orderRepository.save(order);
        return getOrderResponse(order);
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " does not exist."));
        return getOrderResponse(order);
    }

    public List<OrderResponse> getOrdersBetweenDates(Date startDate, Date endDate) {
        return orderRepository.findAllByCreatedAtBetween(startDate, endDate, Pageable.unpaged()).stream()
                .map(OrderServiceImpl::getOrderResponse).collect(Collectors.toList());
    }

    static OrderResponse getOrderResponse(Order order) {
        List<BookOrderResponse> bookOrderResponses = order.getBookOrders().stream()
                .map(bookOrder
                        -> new BookOrderResponse(bookOrder.getId(), bookOrder.getBook().getId(), bookOrder.getCount()))
                .collect(Collectors.toList());
        return new OrderResponse(order.getId(), order.getCustomer().getId(), order.getCreatedAt(), bookOrderResponses);
    }
}
