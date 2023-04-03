package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCustomer(Customer customer, Pageable pageable);

    Page<Order> findAllByCreatedAtBetween(Date startDate, Date endDate, Pageable pageable);
}
