package com.getir.readingisgood.response;

import java.util.Date;
import java.util.List;

public class OrderResponse {
    private final Long id;

    private final Long customerId;

    private final Date createdAt;

    private final List<BookOrderResponse> bookOrders;

    public OrderResponse(Long id, Long customerId, Date createdAt, List<BookOrderResponse> bookOrders) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.bookOrders = bookOrders;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<BookOrderResponse> getBookOrders() {
        return bookOrders;
    }
}
