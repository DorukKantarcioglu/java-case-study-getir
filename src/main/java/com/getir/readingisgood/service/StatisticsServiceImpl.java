package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.exception.EntityNotFoundException;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.response.StatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public StatisticsServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public List<StatisticsResponse> getMonthlyOrderStatistics(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + customerId + " does not exist."));
        List<Order> orders = orderRepository.findByCustomer(customer, Pageable.unpaged()).getContent();
        Map<Month, List<Order>> monthStatisticsMap = orders.stream().collect(Collectors.groupingBy(order ->
                order.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).getMonth()));
        return monthStatisticsMap.entrySet().stream()
                .map(entry -> getStatisticsResponse(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private static StatisticsResponse getStatisticsResponse(Month month, List<Order> orders) {
        long totalBookCount = 0;
        double totalPurchasedAmount = 0;
        for (Order order : orders) {
            for (BookOrder bookOrder : order.getBookOrders()) {
                totalBookCount += bookOrder.getCount();
                totalPurchasedAmount += bookOrder.getBook().getPrice() * bookOrder.getCount();
            }
        }
        return new StatisticsResponse(month.toString(), (long) orders.size(), totalBookCount, totalPurchasedAmount);
    }
}
